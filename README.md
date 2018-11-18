# credit-fraud-neural-networks
> Unsupervised Machine Learning algorithm to predict whether a user will default on loan issued credit or not by using
Neural Networks with back propigation

> Presentation: http://bit.ly/ml-project-slides

> Algorithm Statistics: http://bit.ly/ml-project-stats
___
## How to Run(CMD)
1. Route to dist folder
2. Run the command java -jar NeuralNetworks.jar
## How to Run(IDE)
1. Open with netbeans
2. Hit Run
## Changes to Code(IDE)
1. Rebuild the project
2. Copy the data files in dist folder to run as jar file

## Configuration Changes
`NeuralNetworks.java` file's `main` function contains following configuration parameters for Neural Networks algorithm:

| Parameter                | Description                                                                                                                 |
| ------------------------ | --------------------------------------------------------------------------------------------------------------------------- |
| `file_path`              | The path for data-set file to be read by the algorithm                                                                      |
| `HiddenLayer Size`    | The number of hidden layers in the network           |
| `Neurons`    | The number of neurons in each hidden layer           |
| `learn_rate`             | Learning rate for the Cost function in order to reach _minima_ point.                                                        |
| `epochs`                 | Number of iterations for which to repeat the learning algorithm in order to update weights-matrix. 

| `folds`                 | Number of folds for which model is to be repeatedly built and tested.|
