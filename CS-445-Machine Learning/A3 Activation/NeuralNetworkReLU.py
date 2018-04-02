import neuralnetworksA2

class NeuralNetworkReLU(NeuralNetwork):

	def __init__(self, ni, nhs, no):
		NeuralNetwork.__init__(self, ni, nhs, no)

	def activation(self, weighted_sum):
		return max(0, weighted_sum)

	def activationDerivative(self, activation_value):
		return 0 if activation_value <= 0 else activation_value
