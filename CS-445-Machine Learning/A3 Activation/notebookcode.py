
# coding: utf-8

# $\newcommand{\xv}{\mathbf{x}}
# \newcommand{\Xv}{\mathbf{X}}
# \newcommand{\yv}{\mathbf{y}}
# \newcommand{\zv}{\mathbf{z}}
# \newcommand{\av}{\mathbf{a}}
# \newcommand{\Wv}{\mathbf{W}}
# \newcommand{\wv}{\mathbf{w}}
# \newcommand{\tv}{\mathbf{t}}
# \newcommand{\Tv}{\mathbf{T}}
# \newcommand{\muv}{\boldsymbol{\mu}}
# \newcommand{\sigmav}{\boldsymbol{\sigma}}
# \newcommand{\phiv}{\boldsymbol{\phi}}
# \newcommand{\Phiv}{\boldsymbol{\Phi}}
# \newcommand{\Sigmav}{\boldsymbol{\Sigma}}
# \newcommand{\Lambdav}{\boldsymbol{\Lambda}}
# \newcommand{\half}{\frac{1}{2}}
# \newcommand{\argmax}[1]{\underset{#1}{\operatorname{argmax}}}
# \newcommand{\argmin}[1]{\underset{#1}{\operatorname{argmin}}}$

# # Assignment 3: Activation Functions

# Jim Xu

# ## Overview

# Compare the training and testing performances of networks with tanh and networks with the other activation functions, like ReLU and Swish.

# ### NeuralNetworkReLU

# Define a new class named ```NeuralNetworkReLU``` that extends ```NeuralNetwork``` and defines new implementations of ```activation``` and ```activationDerivative``` that implement the ReLU activation function.

# In[1]:


import numpy as np
import pandas as pd


# In[2]:


import neuralnetworksA2

class NeuralNetworkReLU(neuralnetworksA2.NeuralNetwork):

    def __init__(self, ni, nhs, no):
        neuralnetworksA2.NeuralNetwork.__init__(self, ni, nhs, no)

    def activation(self, weighted_sum):
        return weighted_sum * (weighted_sum > 0)

    def activationDerivative(self, activation_value):
        return np.greater(activation_value, 0).astype(int)


# In[3]:


def partition(X, T, portion, shuffle = True):
    nParts = int(X.shape[0] * portion)
    if shuffle:
        rows = np.arange(X.shape[0])
        np.random.shuffle(rows)
        trainIndices = rows[:nParts]
        testIndices = rows[nParts:]
        return X[trainIndices,:], T[trainIndices,:], X[testIndices,:], T[testIndices,:]
    else:
        return X[:nParts,:], T[:nParts,:], X[nParts:,:], T[nParts:,:]


# In[4]:


def rmse(A, B):
    return np.sqrt(np.mean((A - B)**2))


# ### Comparison in Different Hidden Layer Structures

# Use the [```energydata_complete.csv```](https://archive.ics.uci.edu/ml/machine-learning-databases/00374/) data for the comparisons. $X$ using all columns except ```['date','Appliances', 'rv1', 'rv2']``` and $T$ using just ```'Appliances'```.

# In[5]:


import pandas as pd


# In[6]:


df = pd.read_csv("energydata_complete.csv")
data = df.values
data.shape


# In[7]:


X = np.float64(data[:,2:-2])
T = np.float64(data[:,1].reshape(-1,1))
X.shape, T.shape


# - For each of the two activation functions, ```tanh```, and ```ReLU```:
#   - For each hidden layer structure in [[u]*nl for u in [1, 2, 5, 10, 50] for nl in [1, 2, 3, 4, 5, 10]]:
#       - Repeat 10 times:
#           - Randomly partition the data into training set with 80% of samples and testing set with other 20%.
#           - Create a neural network using the given activation function and hidden layer structure.
#           - Train the network for 100 iterations.
#           - Calculate two RMS errors, one on the training partition and one on the testing partitions.
#       - Calculate the mean of the training and testing RMS errors over the 10 repetitions.

# In[8]:


nns = [neuralnetworksA2.NeuralNetwork, NeuralNetworkReLU]
hls = [[u]*nl for u in [1, 2, 5, 10, 20] for nl in [1, 2, 3, 4, 5]]
rmses = []
for i, nn in enumerate(nns):
    for j, hl in enumerate(hls):
        errors = []
        for nTrain in range(10):
            Xtrain, Ttrain, Xtest, Ttest = partition(X, T, 0.8, shuffle = True)
            nnet = nn(X.shape[1], hl, T.shape[1])
            nnet.train(Xtrain, Ttrain, nIterations = 100)
            errors.append([rmse(Ttrain, nnet.use(Xtrain)), rmse(Ttest, nnet.use(Xtest))])
        rmses.append([nn.__name__, hl] + np.mean(errors, axis = 0).tolist())
rmsepd = pd.DataFrame(rmses)
print(rmsepd)


# In[10]:


import matplotlib.pyplot as plt
get_ipython().run_line_magic('matplotlib', 'inline')


# In[30]:


plt.figure(figsize=(15, 10))

plt.plot(rmsepd.values[:25, 2:], 'o-')
plt.plot(rmsepd.values[25:, 2:], 'o-')
plt.legend(('Tanh Train RMSE', 'Tanh Test RMSE', 'ReLU Train RMSE', 'ReLU Test RMSE'))
plt.xticks(range(rmsepd.shape[0]//2), hls, rotation=30, horizontalalignment='right')
plt.title("NeuralNetwork vs NeuralNetworkReLU")
plt.grid(True)

plt.show()


# The above plot shows that, in general, more complex hidden layer structures yields smaller RMSE for both Training and Testing data. Among all of the hidden layer structures, `[20, 20, 20]` produces the least RMSE for Testing data using `tanh` activation function, `[20, 20, 20, 20]` produces the least RMSE for Testing data using `ReLU` activation function.
# 
# Comparing the two activation functions under specific data set, `tanh` yields better results than `ReLU` since the Testing RMSEs are ususlly lower using `tanh` activation function under different hidden layer structures. However, the two activation functions tend to show similar behaviors when the hidden layer grows bigger and more complex as the Testing RMSEs for two activation functions are approximately the same.

# ### Comparison in Different Training Iterations

# In[21]:


nns = [neuralnetworksA2.NeuralNetwork, NeuralNetworkReLU]
nIters = [10, 50, 100, 200, 500]
besthiddenlayer = [20, 20, 20]
rmses2 = []
for i, nn in enumerate(nns):
    for j, nIter in enumerate(nIters):
        errors = []
        for nTrain in range(3):
            Xtrain, Ttrain, Xtest, Ttest = partition(X, T, 0.8, shuffle = True)
            nnet = nn(X.shape[1], besthiddenlayer, T.shape[1])
            nnet.train(Xtrain, Ttrain, nIterations = nIter)
            errors.append([rmse(Ttrain, nnet.use(Xtrain)), rmse(Ttest, nnet.use(Xtest))])
        rmses2.append([nn.__name__, nIter] + np.mean(errors, axis = 0).tolist())
rmsepd2 = pd.DataFrame(rmses2)
print(rmsepd2)


# In[31]:


plt.figure(figsize=(15, 10))

plt.plot(rmsepd2.values[:5, 2:], 'o-')
plt.plot(rmsepd2.values[5:, 2:], 'o-')
plt.legend(('Tanh Train RMSE', 'Tanh Test RMSE', 'ReLU Train RMSE', 'ReLU Test RMSE'))
plt.xticks(range(rmsepd2.shape[0]//2), nIters)
plt.title("NeuralNetwork vs. NeuralNetworkReLU using Different Training Iterations")
plt.grid(True)

plt.show()


# For this specific dataset, more training iterations will greatly decrease Training RMSE while does not have much improvment on Testing RMSE, where for ReLU, 500 iterations yields higher Testing RMSE than 200 iterations. This indicates large number of iterations will highly possible leads to overfitting problem.
# 
# As shown in the plot, 200 iterations has obvious improvment on both Training and Testing RMSE, while adding more iterations does not significantly increase the accuracy of the model. In summary, 200 iterations will produces the ideal results for this dataset.

# ### Comparison in Swish Activation function

# In[22]:


def sigmoid(x):
    return 1 / (1 + np.exp(-x))

class NeuralNetworkSwish(neuralnetworksA2.NeuralNetwork):

    def __init__(self, ni, nhs, no):
        neuralnetworksA2.NeuralNetwork.__init__(self, ni, nhs, no)

    def activation(self, weighted_sum):
        return weighted_sum * sigmoid(weighted_sum)

    def activationDerivative(self, activation_value):
        return sigmoid(activation_value) * (1 + activation_value * (1 - sigmoid(activation_value)))


# In[23]:


nn = NeuralNetworkSwish
hls = [[u]*nl for u in [1, 2, 5, 10, 20] for nl in [1, 2, 3, 4, 5]]
nIters = [10, 50, 100, 200, 500]
rmses3 = []
for i, hl in enumerate(hls):
    for j, nIter in enumerate(nIters):
        errors = []
        for nTrain in range(3):
            Xtrain, Ttrain, Xtest, Ttest = partition(X, T, 0.8, shuffle = True)
            nnet = nn(X.shape[1], hl, T.shape[1])
            nnet.train(Xtrain, Ttrain, nIterations = nIter)
            errors.append([rmse(Ttrain, nnet.use(Xtrain)), rmse(Ttest, nnet.use(Xtest))])
        rmses3.append([nIter, hl] + np.mean(errors, axis = 0).tolist())
rmsepd3 = pd.DataFrame(rmses3)
print(rmsepd3)


# In[72]:


plt.figure(figsize=(15, 10))
xs = np.arange(0,125,5)
index = np.arange(0, 50, 2)
bar_width = 0.3
opacity = 0.6
colors = ['#624ea7', 'g', 'yellow', 'k', 'maroon']
for i, nIter in enumerate(nIters):
    plt.bar(index + i * bar_width, rmsepd3.values[xs + i, 3], bar_width,            alpha = opacity ,label = str(nIter) + ' iterations', color = colors[i])

plt.legend()
plt.xticks(index + 1, hls, rotation=90, horizontalalignment='right')
plt.xlabel('Hidden Layer Structures')
plt.ylabel('Testing RMSE')
plt.ylim(ymin = 85, ymax = 105)

plt.grid(True)

plt.show()


# Above is a plot of Testing RMSE against different hidden layer structures and number of iterations using `Swish` activation function. In general, deeper and more complex hidden layer structures yield better model accuracy since the height of bars goes down as hidden layer increases; 200 number of iterations yields the better results in complex hidden layer structure, 500 number of iterations gives better results in simple hidden layer structure.
# 
# Among all of the different hidden layer structures and number of iterations using `Swish` activation function, `[20, 20, 20, 20]` hidden layer produces the least RMSE for testing data, and `200` iterations has better performance than fewer or greater number of iterations.

# In[73]:


rmsepd.to_csv(r'rmses.txt', header=None, index=None, sep='\t', mode='a')


# In[ ]:


get_ipython().run_line_magic('run', '-i A3grader.py')

