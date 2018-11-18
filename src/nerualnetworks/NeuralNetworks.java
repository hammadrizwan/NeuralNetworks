package nerualnetworks;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Hammad Rizwan
 */
public class NeuralNetworks {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        // TODO code application logic here
        System.out.println("Enter 1 to select Full Data Set");
        System.out.println("Enter 2 to select Information Gain Set1");
        System.out.println("Enter 3 to select Information Gain Set2");
        System.out.println("Enter 4 to select Gain Ratio Set1");
        System.out.println("Enter 5 to select Gain Ratio Set2");
        Scanner input = new Scanner(System.in);
        int sw = input.nextInt();
        FileReader in=new FileReader("FullData.txt");
        switch (sw) {
            case 1:
                in = new FileReader("FullData.txt");//file reader
                break;
            case 2:
                in = new FileReader("reducedDataSetIG.txt");//file reader
                break;
            case 3:
                in = new FileReader("reducedDataSetIG2.txt");//file reader
                break;
            case 4:
                in = new FileReader("reducedDataSetGain.txt");//file reader
                break;
            case 5:
                in = new FileReader("reducedDataSetGain2.txt");//file reader
                break;
            default:
                System.out.println("Wrong input by default using full dataSet");
                break;
        }

        BufferedReader br = new BufferedReader(in);//read a line at atime
        String line;//temp store line read

        ArrayList<double[]> Data = new ArrayList<double[]>();//store file data

        double[] values;
        while ((line = br.readLine()) != null) {//read all the data into memory and store as 2d array
            System.out.println(line);
            // String[] value = line.split("\\s");
            String[] value = line.split(",");
            values = new double[value.length];//array of size= number of attributes in dataSet
//            System.out.println(value.length);
//            System.exit(1);
            for (int i = 0; i < value.length; i++) {
                values[i] = Double.parseDouble(value[i]);//conver string to double
            }
            Data.add(values);
            values = new double[value.length];
        }

        //3 System.exit(1);
//        for(int i=0;i<Data.size();i++){//print data for testing
//            values= Data.get(i);
//            for(int p=0;p<values.length;p++){
//                System.out.printf("%f ",values[p]);
//            }
//            System.out.println();
//        }
        //Create the Neural Network
        ArrayList<HiddenLayers> hiddenLayers = new ArrayList<HiddenLayers>();//Store hidden layers
        HiddenLayers layer = new HiddenLayers();//create initial layer(same size as number of attributes)
        for (int i = 0; i < Data.get(0).length; i++) {//create initial layer neurons
            String name = "Neuron" + Integer.toString(i + 1);//neuron name
            Neuron neuron = new Neuron(name);//create new neuron
            layer.insertNode(neuron);//insert neuron into layer
        }
        hiddenLayers.add(layer);//insert layer into list of layers

        int numberOfHiddenLayers;//get number of hidden layers
        int numberOfNeurons;//holder for number of neurons in a layer
        System.out.println("Enter the number of hidden layers");
        numberOfHiddenLayers = input.nextInt();//hidden layer 1

        for (int i = 1; i <= numberOfHiddenLayers; i++) {
            layer = new HiddenLayers();
            hiddenLayers.add(layer);
            System.out.printf("Enter the number of neurons in hidden layers in layer %d", i);
            numberOfNeurons = input.nextInt();
            for (int j = 0; j < numberOfNeurons; j++) {//add neurons to hidden layer
                String name = "Neuron" + Integer.toString(i) + "0" + Integer.toString(j);
                Neuron neuron = new Neuron(name);
                for (int p = 0; p < hiddenLayers.get(i - 1).LayerSize(); p++) {//add connections to previous layer
                    String connectionName = hiddenLayers.get(i - 1).getNeuronAt(p).getId() + "->" + neuron.getId();
                    Connections connection = new Connections(neuron, hiddenLayers.get(i - 1).getNeuronAt(p), connectionName);
                    neuron.addInConnections(connection);
                    hiddenLayers.get(i - 1).getNeuronAt(p).addOutConnections(connection);
                }
                hiddenLayers.get(i).insertNode(neuron);
            }
        }
        String name = "NeuronFinal";//neuron name
        Neuron neuron = new Neuron(name);
        layer = new HiddenLayers();
        layer.insertNode(neuron);
        for (int p = 0; p < hiddenLayers.get(hiddenLayers.size() - 1).LayerSize(); p++) {//add connections to previous layer
            String connectionName = hiddenLayers.get(hiddenLayers.size() - 1).getNeuronAt(p).getId() + "->" + neuron.getId();
            Connections connection = new Connections(neuron, hiddenLayers.get(hiddenLayers.size() - 1).getNeuronAt(p), connectionName);
            neuron.addInConnections(connection);
            hiddenLayers.get(hiddenLayers.size() - 1).getNeuronAt(p).addOutConnections(connection);

        }
        hiddenLayers.add(layer);
        //the neural network has been made ggwp 
        for (int i = 0; i < hiddenLayers.size(); i++) {//just for testint the network
            for (int p = 0; p < hiddenLayers.get(i).LayerSize(); p++) {
                hiddenLayers.get(i).getNeuronAt(p).printId();
                System.out.println("From");
                for (int k = 0; k < hiddenLayers.get(i).getNeuronAt(p).inputConnections.size(); k++) {
                    System.out.printf("%s ", hiddenLayers.get(i).getNeuronAt(p).inputConnections.get(k).fromNeuron.getId());
                }
                System.out.println("\nTo");
                for (int k = 0; k < hiddenLayers.get(i).getNeuronAt(p).outputConnections.size(); k++) {
                    System.out.printf("%s ", hiddenLayers.get(i).getNeuronAt(p).outputConnections.get(k).toNeuron.getId());
                }
                System.out.println("\n\n");
                // System.out.println(Arrays.toString(hiddenLayers.get(i).getNeuronAt(p).inputConnections.toArray()));
                //System.out.println(Arrays.toString(hiddenLayers.get(i).getNeuronAt(p).outputConnections.toArray()));
            }
        }
        //System.out.println(hiddenLayers.size() - 2);

        //add output layers
//        System.out.println(hiddenLayers.size());
//        System.out.println(hiddenLayers.size()-1);
//        System.exit(1);
        int sizeOfDataSet = 1;
        ArrayList<double[]> dataTraining = new ArrayList<double[]>();//store training data
        ArrayList<double[]> dataTesting = new ArrayList<double[]>();//store test data
        createDataSets(Data, dataTraining, dataTesting);//create training and testing data

        //double[][] outputs = new double[Data.size()][2];
        System.out.println("Please enter the learningRate [0-1]");//learning rate
        double learningRate = input.nextDouble();
        System.out.println("Please enter the number of Epochs");//epochs
        int epochs = input.nextInt();
        System.out.println("Please enter the number of Folds in K-folds cross-validation:");//Kfolds validation
        int folds = input.nextInt();
        double[] overallAccuracy = new double[folds];//store accuracy
        double[] fraudAccuracy = new double[folds];//store frauds
        double[] recall = new double[folds];//store recall
        //hence we have constructed the neural network.
        for (int fold = 0; fold < folds; fold++) {
            System.out.println("Creating model for fold number->" + fold);
            for (int ep = 0; ep < epochs; ep++) {
                //now for FORWARD and BACK PROPIGATION____________________________________________________________________________________
                for (int i = 0; i < dataTraining.size(); i++) {//replace the number 1 with size of vector X
                    values = dataTraining.get(i);
                    //System.out.println("Outputs");
                    //First layer outputs the input
                    for (int p = 0; p < hiddenLayers.get(0).LayerSize() - 1; p++) {//for the first input layer just get input of the dataX_i and put as output
                        hiddenLayers.get(0).getNeuronAt(p).setOutput(values[p]);//sets the first layer output same as input
                        //System.out.println("firstlayer" + p + "\t" + hiddenLayers.get(0).getNeuronAt(p).getOutput());
                    }
                    //Calculate output at hidden layers
                    for (int p = 1; p <= hiddenLayers.size() - 2; p++) {//for each hidden layer
                        for (int c = 0; c < hiddenLayers.get(p).LayerSize(); c++) {//all the nodes in the hidden layer
                            hiddenLayers.get(p).getNeuronAt(c).calculateOutputSigmoid();//output of previous available
                            //    System.out.printf("%s\t%f \n", hiddenLayers.get(p).getNeuronAt(c).getId(), hiddenLayers.get(p).getNeuronAt(c).getOutput());
                        }
                    }
                    //calculate output at outputLayer and generate error
                    for (int c = 0; c < hiddenLayers.get(hiddenLayers.size() - 1).LayerSize(); c++) {//all the nodes in the hidden layer runs only 1 time
                        hiddenLayers.get(hiddenLayers.size() - 1).getNeuronAt(c).calculateOutputSigmoid();//output of previous available
                        double output = hiddenLayers.get(hiddenLayers.size() - 1).getNeuronAt(c).getOutput();
                        //System.out.printf("Classified probability >0.5=2 <0.5=1 %f\n", (output));
                        // System.out.printf("Classified orignally as %f\n", values[values.length - 1]);
                        hiddenLayers.get(hiddenLayers.size() - 1).getNeuronAt(c).calculateErrorOutputLayer(output, values[values.length - 1]);//output calculated at ouput layers
                        //System.out.println("Error at output\t"+hiddenLayers.get(hiddenLayers.size() - 1).getNeuronAt(c).getError());//output calculated at ouput layers
//                use the below code of wieght vector is needed
//                   if(output>0.5){
//                       outputs[i][0]=2;//the generated output 0
//                   }
//                   else{
//                       outputs[i][0]=1;//the generated output 0
//                   }
//                outputs[i][1]=values[values.length - 1];//the orignal output 1
                    }
                    for (int p = hiddenLayers.size() - 2; p > 0; p--) {//for each hidden layer

                        for (int c = 0; c < hiddenLayers.get(p).LayerSize(); c++) {//all the nodes in the hidden layer
                            hiddenLayers.get(p).getNeuronAt(c).calculateErrorHiddenLayer();//calculate error
                            //System.out.println("Error layer-"+p+"->"+hiddenLayers.get(p).getNeuronAt(c).getError());
                        }
                    }

                    //RECALCULATION WIEGHTS
                    for (int c = 0; c < hiddenLayers.get(hiddenLayers.size() - 1).LayerSize(); c++) {//all the nodes in the hidden layer runs only 1 time
                        hiddenLayers.get(hiddenLayers.size() - 1).getNeuronAt(c).updateWieghtsOutputLayer(learningRate);
                    }
                    for (int p = hiddenLayers.size() - 2; p > 0; p--) {//for each hidden layer
                        for (int c = 0; c < hiddenLayers.get(p).LayerSize(); c++) {//all the nodes in the hidden layer
                            hiddenLayers.get(p).getNeuronAt(c).updateWieghtHiddenLayers(learningRate);//output of previous available
                        }
                        //System.out.println();
                    }
//           System.exit(1);

                    //Training Ended
                }
            }
            System.out.println("Testing model for fold number->" + fold);
            System.out.println("\n\n\n\n");
//        
            double truePositive = 0;
            double falsePositive = 0;
            double trueNegative = 0;
            double falseNegative = 0;
            for (int i = 0; i < dataTesting.size(); i++) {//replace the number 1 with size of vector X
                //First layer outputs the input
                values = dataTesting.get(i);
                for (int p = 0; p < hiddenLayers.get(0).LayerSize() - 1; p++) {//for the first input layer just get input of the dataX_i and put as output
                    hiddenLayers.get(0).getNeuronAt(p).setOutput(values[p]);//sets the first layer output same as input
                    //System.out.println("firstlayer" + p + "\t" + hiddenLayers.get(0).getNeuronAt(p).getOutput());
                }
                //Calculate output at hidden layers
                for (int p = 1; p <= hiddenLayers.size() - 2; p++) {//for each hidden layer
                    for (int c = 0; c < hiddenLayers.get(p).LayerSize(); c++) {//all the nodes in the hidden layer
                        hiddenLayers.get(p).getNeuronAt(c).calculateOutputSigmoid();//output of previous available
                        //  System.out.printf("%s\t%f \n", hiddenLayers.get(p).getNeuronAt(c).getId(), hiddenLayers.get(p).getNeuronAt(c).getOutput());
                    }
                }
                //calculate output at outputLayer and generate error
                for (int c = 0; c < hiddenLayers.get(hiddenLayers.size() - 1).LayerSize(); c++) {//all the nodes in the hidden layer runs only 1 time
                    double output = hiddenLayers.get(hiddenLayers.size() - 1).getNeuronAt(c).calculateOutputStepFunction();//output of previous available
                    if (output == 1 && values[values.length - 1] == 1) {
                        truePositive++;
                    } else if (output == 2 && values[values.length - 1] == 2) {
                        trueNegative++;
                    } else if (output == 1 && values[values.length - 1] == 2) {
                        falsePositive++;
                    } else {
                        falseNegative++;
                    }
//                

//                    System.out.printf("Classified probability >=0.5=2 <0.5=1 %f\n", output);
//                    System.out.printf("Classified orignally as %f\n", values[values.length - 1]);
                }

            }
            System.out.println("correcltyLabelledNonFraud(True Positive) ->\t" + truePositive);
            System.out.println("incorrecltyLabelledNonFraud(True Negative) ->\t" + falsePositive);
            System.out.println("correcltyLabelledFraud(True Negative) ->\t" + trueNegative);
            System.out.println("incorrecltyLabelledFraud(false Negative) ->\t" + falseNegative);
            
            
            overallAccuracy[fold] = ((truePositive + trueNegative) / (truePositive + trueNegative + falseNegative + falsePositive)) * 100;
            System.out.println("Accuracy overall=\t" + overallAccuracy[fold] + "%");
            if(Double.isNaN((trueNegative / (trueNegative + falseNegative) * 100))){
                fraudAccuracy[fold] = 0;
            }
            else{
                fraudAccuracy[fold] = (trueNegative / (trueNegative + falseNegative) * 100);
            }
            
            System.out.println("Precision=\t" + fraudAccuracy[fold] + "%");
            if(Double.isNaN((trueNegative / (trueNegative + falseNegative) * 100))){
                recall[fold] = 0;
            }
            else{
                recall[fold] = (trueNegative / (trueNegative + falsePositive) * 100);
            }
            recall[fold] = (trueNegative / (trueNegative + falsePositive) * 100);
            System.out.println("Recall=\t" + recall[fold] + "%");
            resetLayers(hiddenLayers);

        }

        double overallAccuracyAverage = 0;
        double fraudAccuracyAverage = 0;
        double recallAverage = 0;
        for (int i = 0; i < folds; i++) {
            overallAccuracyAverage += overallAccuracy[i];
            fraudAccuracyAverage += fraudAccuracy[i];
            recallAverage += recall[i];
        }
        overallAccuracyAverage = (overallAccuracyAverage / folds);
        fraudAccuracyAverage = (fraudAccuracyAverage / folds);
        recallAverage = (recallAverage / folds);
        System.out.println("Accuracy Average overall=\t" + overallAccuracyAverage + "%");
        System.out.println("Precision Average =\t" + fraudAccuracyAverage + "%");
        System.out.println("Recall Average=\t" + recallAverage + "%");

    }

    //create test and training set by 70/30 split hence take random 300 records for testing
    public static void createDataSets(ArrayList<double[]> Data, ArrayList<double[]> dataTraining, ArrayList<double[]> dataTesting) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 300; i++) {
            int number = (int) Math.floor((Math.random() * (1000 - (1))) + (1));
            if (list.contains(number)) {
                continue;
            } else {
                list.add(number);
            }
        }
        for (int i = 0; i < Data.size(); i++) {
            if (list.contains(i)) {
                dataTesting.add(Data.get(i));
            } else {
                dataTraining.add(Data.get(i));
            }
        }

    }

    public static void resetLayers(ArrayList<HiddenLayers> hiddenLayers) {//reset the neural network for the next fold
        for (int i = 0; i < hiddenLayers.size(); i++) {//just for testint the network
            for (int p = 0; p < hiddenLayers.get(i).LayerSize(); p++) {

                for (int k = 0; k < hiddenLayers.get(i).getNeuronAt(p).inputConnections.size(); k++) {
                    hiddenLayers.get(i).getNeuronAt(p).inputConnections.get(k).reset();
                }
                for (int k = 0; k < hiddenLayers.get(i).getNeuronAt(p).outputConnections.size(); k++) {
                    hiddenLayers.get(i).getNeuronAt(p).outputConnections.get(k).reset();
                }
                hiddenLayers.get(i).getNeuronAt(p).reset();
            }
        }
    }
}

//double correctlyLabelled = 0;
//        double incorrectlyLabelled = 0;
//        double labelledWrong = 0;
//        double labelledRight = 0;
// if (output == 1 && values[values.length - 1] == 2) {
//                    incorrectlyLabelled++;
//                } else if (output == 2 && values[values.length - 1] == 2) {
//                    correctlyLabelled++;
//                }
//                if (output == values[values.length - 1]) {
//                    labelledRight++;
//                } else {
//                    labelledWrong++;
//                }
//System.out.println("correcltyLabelled fraud->\t" + correctlyLabelled);
//        System.out.println("incorrecltyLabelled fraud->\t" + incorrectlyLabelled);
//        double accuracy = (correctlyLabelled / (incorrectlyLabelled + correctlyLabelled)) * 100;
//        System.out.println("Accuracy for fraud=\t" + accuracy + "%");
//        System.out.println("correcltyLabelled ->\t" + labelledRight);
//        System.out.println("incorrecltyLabelled ->\t" + labelledWrong);
//        double accuracy1 = (labelledRight / (labelledWrong + labelledRight)) * 100;
//        System.out.println("Accuracy overall=\t" + accuracy1 + "%");

