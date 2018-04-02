import os
import numpy as np

print('\n======================= Code Execution =======================\n')

assignmentNumber = '3'

if False:
    runningInNotebook = False
    print('========================RUNNING INSTRUCTOR''S SOLUTION!')
    # import A2mysolution as useThisCode
    # train = useThisCode.train
    # trainSGD = useThisCode.trainSGD
    # use = useThisCode.use
    # rmse = useThisCode.rmse
else:
    import subprocess, glob, pathlib
    filename = next(glob.iglob('*-A{}.ipynb'.format(assignmentNumber)), None)
    print('Extracting python code from notebook named \'{}\' and storing in notebookcode.py'.format(filename))
    if not filename:
        raise Exception('Please rename your notebook file to <Your Last Name>-A{}.ipynb'.format(assignmentNumber))
    with open('notebookcode.py', 'w') as outputFile:
        subprocess.call(['jupyter', 'nbconvert', '--to', 'script',
                         '*-A{}.ipynb'.format(assignmentNumber), '--stdout'], stdout=outputFile)
    # from https://stackoverflow.com/questions/30133278/import-only-functions-from-a-python-file
    import sys
    import ast
    import types
    with open('notebookcode.py') as fp:
        tree = ast.parse(fp.read(), 'eval')
    print('Removing all statements that are not function or class defs or import statements.')
    for node in tree.body[:]:
        if (not isinstance(node, ast.FunctionDef) and
            not isinstance(node, ast.ClassDef) and
            not isinstance(node, ast.Import) and
            not isinstance(node, ast.ImportFrom)):
            tree.body.remove(node)
    # Now write remaining code to py file and import it
    module = types.ModuleType('notebookcodeStripped')
    code = compile(tree, 'notebookcodeStripped.py', 'exec')
    sys.modules['notebookcodeStripped'] = module
    exec(code, module.__dict__)
    # import notebookcodeStripped as useThisCode
    from notebookcodeStripped import *

def close(a, b, within=0.01):
    return abs(a-b) < within

g = 0

print('\nTesting  import neuralnetworksA2 as nn')
points = 5
try:
    import neuralnetworksA2 as nn
    g += points
    print('\n--- {}/{} points. The statement  import neuralnetworksA2 as nn  works.'.format(points, points))
except Exception as ex:
    print('\n--- 0/{} points. The statement  import neuralnetworksA2 as nn  does not work.'.format(points))
    

print('''\nTesting nnet = nn.NeuralNetwork(1, 10, 1)''')
points = 5
try:
    nnet = nn.NeuralNetwork(1, 10, 1)
    g += points
    print('\n--- {}/{} points. nnet correctly constructed'.format(points, points))
except Exception as ex:
    print('\n--- 0/{} points. nnet not correctly constructed'.format(points))

print('''\nTesting a = nnet.activation(-0.8)''')
points = 5
try:
    a = nnet.activation(-0.8)
    correctAnswer = -0.664
    if close(a, correctAnswer):
        g += points
        print('\n--- {}/{} points. activation of {} is correct.'.format(points, points, a))
    else:
        print('\n--- 0/{} points. activation of {} is not correct. It should be {}.'.format(points, a, correctAnswer))

except Exception as ex:
    print('\n--- 0/{} points. The call to activation raised exception\n {}'.format(points, ex))
    

print('''\nTesting da = nnet.activationDerivative(-0.664)''')
points = 5
try:
    a = nnet.activationDerivative(-0.664)
    correctAnswer = 0.55906
    if close(a, correctAnswer):
        g += points
        print('\n--- {}/{} points. activationDerivative of {} is correct.'.format(points, points, a))
    else:
        print('\n--- 0/{} points. activationDerivative of {} is not correct. It should be {}.'.format(points, a, correctAnswer))
except Exception as ex:
    print('\n--- 0/{} points. The call to activationDerivative raised exception\n {}'.format(points, ex))


######################################################################
# Now NeuralNetworkReLU

print('''\nTesting nnetrelu = NeuralNetworkReLU(1, 5, 1)''')
points = 5
try:
    nnetrelu = NeuralNetworkReLU(1, 5, 1)
    g += points
    print('\n--- {}/{} points. nnet correctly constructed'.format(points, points))
except Exception as ex:
    print('''\nTesting nnetrelu = nn.NeuralNetworkReLU(1, 5, 1)''')
    try:
        nnetrelu = nn.NeuralNetworkReLU(1, 5, 1)
        g += points
        print('\n--- {}/{} points. nnet correctly constructed'.format(points, points))
    except Exception as ex:
        print('\n--- 0/{} points. nnet not correctly constructed'.format(points))

print('''\nTesting a = nnetrelu.activation(-0.8)''')
points = 5
try:
    a = nnetrelu.activation(-0.8)
    correctAnswer = 0.0
    if close(a, correctAnswer):
        g += points
        print('\n--- {}/{} points. activation of {} is correct.'.format(points, points, a))
    else:
        print('\n--- 0/{} points. activation of {} is not correct. It should be {}.'.format(points, a, correctAnswer))

except Exception as ex:
    print('\n--- 0/{} points. The call to activation raised exception\n {}'.format(points, ex))
    
print('''\nTesting a = nnetrelu.activation(1.8)''')
points = 5
try:
    a = nnetrelu.activation(1.8)
    correctAnswer = 1.8
    if close(a, correctAnswer):
        g += points
        print('\n--- {}/{} points. activation of {} is correct.'.format(points, points, a))
    else:
        print('\n--- 0/{} points. activation of {} is not correct. It should be {}.'.format(points, a, correctAnswer))

except Exception as ex:
    print('\n--- 0/{} points. The call to activation raised exception\n {}'.format(points, ex))
    

print('''\nTesting da = nnetrelu.activationDerivative(0.0)''')
points = 5
try:
    a = nnetrelu.activationDerivative(0.0)
    correctAnswer = 0.0
    if close(a, correctAnswer):
        g += points
        print('\n--- {}/{} points. activationDerivative of {} is correct.'.format(points, points, a))
    else:
        print('\n--- 0/{} points. activationDerivative of {} is not correct. It should be {}.'.format(points, a, correctAnswer))
except Exception as ex:
    print('\n--- 0/{} points. The call to activationDerivative raised exception\n {}'.format(points, ex))

print('''\nTesting da = nnetrelu.activationDerivative(5.5)''')
points = 5
try:
    a = nnetrelu.activationDerivative(5.5)
    correctAnswer = 1.0
    if close(a, correctAnswer):
        g += points
        print('\n--- {}/{} points. activationDerivative of {} is correct.'.format(points, points, a))
    else:
        print('\n--- 0/{} points. activationDerivative of {} is not correct. It should be {}.'.format(points, a, correctAnswer))
except Exception as ex:
    print('\n--- 0/{} points. The call to activationDerivative raised exception\n {}'.format(points, ex))

    
X = np.arange(18).reshape((6, 3))
T = X[:,0:1] - X[:, 2:3]

print('''\nTesting X = np.arange(18).reshape((6, 3))
        T = X[:,0:1] - X[:, 2:3]
        Xtrain, Ttrain, Xtest, Ttest = partition(X, T, 0.5, shuffle=False)''')
X = np.arange(18).reshape((6, 3))
T = X[:,0:1] - X[:, 2:3]
Xtrain, Ttrain, Xtest, Ttest = partition(X, T, 0.5, shuffle=False)
points = 15
try:
    if (np.all(Xtrain == X[:3, :]) and
        np.all(Ttrain == T[:3, :]) and
        np.all(Xtest == X[3:, :]) and
        np.all(Ttest == T[3:, :])):
        g += points
        print('\n--- {}/{} points. partition result is correct.'.format(points, points))
    else:
        print('\n--- 0/{} points. partition result is not correct.'.format(points))
        print('             Correct Xtrain is')
        print(X[:3, :])
        print('             Your answer is')
        print(Xtrain)
        print('             Correct Ttrain is')
        print(T[:3, :])
        print('             Your answer is')
        print(Ttrain)
        print('             Correct Xtest is')
        print(X[3:, :])
        print('             Your answer is')
        print(Xtest)
        print('             Correct Ttest is')
        print(T[3:, :])
        print('             Your answer is')
        print(Ttest)
except Exception as ex:
    print('\n--- 0/{} points. The call to partition raised exception\n {}'.format(points, ex))



name = os.getcwd().split('/')[-1]

print('\n{} Execution Grade is {}/60'.format(name, g))

print('\n============= Experiments with Energy Data =============')

print('\n--- _/20 points. Correct implementation of procedure for collecting RMSEs averaged over 10 runs.\nComments:')

print('''\n--- _/10 points. Correct plot with four curves for training and testing RMSEs for the two activation functions.
                 Label y axis as "RMSE".
                 Label x axis with hidden layer structures rotated a bit so they can be read.
                 Include legend with brief label for each curve.
Comments:''')


print('''\n--- _/10 points. Discussion of what you observe in the plot.
Comments:''')


print('\n{} Notebook Grade is __ / 40'.format(name))

print('\n{} FINAL GRADE is __ / 100'.format(name))



