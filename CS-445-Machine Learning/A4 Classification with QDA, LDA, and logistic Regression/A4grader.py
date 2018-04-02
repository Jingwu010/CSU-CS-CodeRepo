import os
import numpy as np

print('\n======================= Code Execution =======================\n')

assignmentNumber = '4'

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

g = 0

print('\nTesting  import neuralnetworksA4 as nn')
points = 5
try:
    import neuralnetworksA4 as nn
    g += points
    print('\n--- {}/{} points. The statement  import neuralnetworksA4 as nn  works.'.format(points, points))
except Exception as ex:
    print('\n--- 0/{} points. The statement  import neuralnetworksA4 as nn  does not work.'.format(points))
    
print('\nTesting  import mlutilities as ml')
points = 5
try:
    import mlutilities as ml
    g += points
    print('\n--- {}/{} points. The statement  import mlutilities as ml  works.'.format(points, points))
except Exception as ex:
    print('\n--- 0/{} points. The statement  import mlutilities as ml  does not work.'.format(points))
    

X = np.vstack((np.arange(20), [7, 4, 5, 5, 8, 4, 6, 7, 4, 9, 4, 2, 6, 6, 3, 3, 7, 2, 6, 4])).T
T = np.array([1]*8 + [2]*8 + [3]*4).reshape((-1, 1))
XtrainAnswer = np.array([[ 0,  7],
                         [ 1,  4],
                         [ 2,  5],
                         [ 3,  5],
                         [ 4,  8],
                         [ 5,  4],
                         [ 8,  4],
                         [ 9,  9],
                         [10,  4],
                         [11,  2],
                         [12,  6],
                         [13,  6],
                         [16,  7],
                         [17,  2],
                         [18,  6]])
TtrainAnswer = np.array([[1],
                         [1],
                         [1],
                         [1],
                         [1],
                         [1],
                         [2],
                         [2],
                         [2],
                         [2],
                         [2],
                         [2],
                         [3],
                         [3],
                         [3]])

XtestAnswer = np.array([[ 6,  6],
                        [ 7,  7],
                        [14,  3],
                        [15,  3],
                        [19,  4]])
TtestAnswer = np.array([[1],
                        [1],
                        [2],
                        [2],
                        [3]])


print('''\nTesting X = np.vstack((np.arange(20), [7, 4, 5, 5, 8, 4, 6, 7, 4, 9, 4, 2, 6, 6, 3, 3, 7, 2, 6, 4])).T
        T = np.array([1]*8 + [2]*8 + [3]*4).reshape((-1, 1))
        Xtrain, Ttrain, Xtest, Ttest = ml.partition(X, T, 0.8, classification=True, shuffle=False)''')
points = 10
try:
    Xtrain, Ttrain, Xtest, Ttest = ml.partition(X, T, 0.8, classification=True, shuffle=False)
    if np.all(Xtrain == XtrainAnswer) and np.all(Xtest == XtestAnswer) and np.all(Ttrain == TtrainAnswer) and np.all(Ttest == TtestAnswer):
        g += points
        print('\n--- {}/{} points. ml.partition works correctly'.format(points, points))
    else:
        print('\n--- 0/{} points. ml.partition did not work correctly.'.format(points))
        print('Xtrain should be')
        print(XtrainAnswer)
        print('Yours is')
        print(Xtrain)
        print('Ttrain should be')
        print(TtrainAnswer)
        print('Yours is')
        print(Ttrain)
        print('Xtest should be')
        print(XtestAnswer)
        print('Yours is')
        print(Xtest)
except Exception as ex:
    print('\n--- 0/{} points. ml.partition raised exception.'.format(points))
    print(ex)
        

print('''\nTesting nnet = nn.NeuralNetworkClassifier(2, [5, 5], 3)''')
points = 10
try:
    nnet = nn.NeuralNetworkClassifier(2, [5, 5], 3)
    g += points
    print('\n--- {}/{} points. nn.NeuralNetworkClassifier did not throw exception.'.format(points, points))
except Exception as ex:
    print('\n--- 0/{} points. nn.NeuralNetworkClassifier raised exception.'.format(points))
    print(ex)
        
print('''\nTesting nnet.train(Xtrain, Ttrain, 200)''')
points = 20
try:
    nnet.train(Xtrain, Ttrain, 200)
    if nnet.getErrors()[-1] < 1.e-5:
        g += points
        print('\n--- {}/{} points. nnet.getErrors()[-1] is less than 1.e-5.'.format(points, points))
    else:
        print('\n--- 0/{} points. nnet.getErrors()[-1] is not less than 1.e-5.'.format(points))
except Exception as ex:
    print('\n--- 0/{} points. nnet.train or nnet.getErrors raised exception.'.format(points))
    print(ex)
        
        
print('''\nTesting Ytest = nnet.use(Xtest)
        fractionCorrect = np.sum(Ytest == Ttest) / len(Ttest)''')
points = 20
try:
    Ytest = nnet.use(Xtest)
    fractionCorrect = np.sum(Ytest == Ttest) / len(Ttest)
    if fractionCorrect == 0.6:
        g += points
        print('\n--- {}/{} points. fractionCorrect is correct with value of {}.'.format(points, points, fractionCorrect))
    else:
        print('\n--- 0/{} points. fractionCorrect is wrong. It should be 0.6, but you have {}.'.format(points, fractionCorrect))
except Exception as ex:
    print('\n--- 0/{} points. Exception raised.'.format(points))
    print(ex)
        

print('''\nTesting Ytest, Yprobs, Z = nnet.use(Xtest, allOutputs=True)''')
points = 10
try:
    Ytest, Yprobs, Z = nnet.use(Xtest, allOutputs=True)
    s = np.sum(Yprobs, 1)
    if np.all(s > 0.98):
        g += points
        print('\n--- {}/{} points. np.sum(Yprobs, axis=1) is correct: are all very close to 1.'.format(points, points))
    else:
        print('\n--- 0/{} points. np.sum(Yprobs, axis=1) is wrong: all should be very close to 1.'.format(points, points))
        print('                    Yours are', s)
except Exception as ex:
    print('\n--- 0/{} points. Exception raised.'.format(points))
    print(ex)
        

name = os.getcwd().split('/')[-1]

print('\n{} Execution Grade is {}/80'.format(name, g))

print('\n============= Experiments with Orthopedic Data =============')

print('\n--- __/5 points. Results for QDA, including percents correct and confusion matrices.')
print('                Comments:')

print('\n--- __/5 points. Results for LDA, including percents correct and confusion matrices.')
print('                Comments:')

print('\n--- __/5 points. Results for several neural networks, including percents correct and confusion matrices.')
print('                Comments:')

print('\n--- __/5 points. Discussion of relative performance among classifiers, and among the different classes')
print('                Comments:')


print('\n{} Notebook Grade is __ / 20'.format(name))

print('\n{} FINAL GRADE is __ / 100'.format(name))


print('\n{} EXTRA CREDIT POINTS  __ / 1'.format(name))


