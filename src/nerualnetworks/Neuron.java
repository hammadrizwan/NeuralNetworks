/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nerualnetworks;

import java.util.ArrayList;

/**
 *
 * @author Hammad
 */
public class Neuron {

    String id;
    ArrayList<Connections> inputConnections;
    ArrayList<Connections> outputConnections;
    double output;//stores ouput
    double error;//error at this neuron
    double alpha;//momentum multyplier
    double deltaW;//W(i-1)
    double scaler;//multyplier for new error function

    public double setOutput(double output) {
        return this.output = output;
    }

    public double getOutput() {
        return output;
    }

    public Neuron(String id) {
        this.id = id;//neuron id
        this.inputConnections = new ArrayList<Connections>();
        this.outputConnections = new ArrayList<Connections>();
        this.alpha = 0.3;//momentum multyplier
        this.deltaW = 0;//W(i-1)
        this.scaler = 3;//scaler Multyplier for New Error/Punishing error function
    }

    public void reset() {//reset
        this.output = 0;
        this.deltaW = 0;
        this.error = 0;
    }

    public void addInConnections(Connections connection) {//incoming edges
        this.inputConnections.add(connection);
    }

    public void addOutConnections(Connections connection) {//outgoing edges
        this.outputConnections.add(connection);
    }

    public double calculateOutputStepFunction() {//output final layer neuron
        double outputs = 0;
        for (int i = 0; i < this.inputConnections.size(); i++) {
            outputs += this.inputConnections.get(i).fromNeuron.getOutput() * this.inputConnections.get(i).wieght;
        }
        outputs = this.ActivationFuntionSigmoid(outputs);
        //System.out.println("hereOutputs\t"+outputs);
        if (outputs > 0.5) {
            return 2;
        } else {
            return 1;
        }
    }

    public void calculateOutputSigmoid() {//calculate output hiddenlayers
        double outputs = 0;
        for (int i = 0; i < inputConnections.size(); i++) {
            outputs += inputConnections.get(i).fromNeuron.getOutput() * inputConnections.get(i).wieght;
        }

        this.output = this.ActivationFuntionSigmoid(outputs);
    }

    public double ActivationFuntionSigmoid(double value) {//sigmoid function
        return (1 / (1 + Math.pow(Math.E, (-1 * value))));
    }
//    public void calculateErrorOutputLayer(double[][] Outputs){
//        double calculation=0;
//        for(int i=0;i<Outputs.length;i++){
//            calculation+=(Outputs[i][1]-Outputs[i][0])*(Outputs[i][0])*(1-Outputs[i][1]);//(t-o)*(o)*((1-o)
//        }
//        this.error=-1*calculation;//-(t-o)*(o)*((1-o)
//        
//    }

    public void calculateErrorOutputLayer(double predictedOutput, double orignalOutput) {//callculate error
        this.error = ((orignalOutput - 1) - (predictedOutput)) * (predictedOutput) * (1 - (predictedOutput));//(t-o)*o*(1-o)
        //System.out.println(this.id+"-error->"+this.error);
//        if((orignalOutput-1)==1 && predictedOutput ==0){
//            this.error=((orignalOutput-1)-(predictedOutput))*(predictedOutput)*(1-(predictedOutput))*this.scaler;//(t-o)*o*(1-o)
//        }
//        else{
//            this.error=((orignalOutput-1)-(predictedOutput))*(predictedOutput)*(1-(predictedOutput));
//        }

    }

    public void updateWieghtsOutputLayer(double learningRate) {//update edges connected to ouput neuron

        for (int i = 0; i < this.inputConnections.size(); i++) {

            // System.out.println(this.inputConnections.get(i).getName()+"->\t"+this.inputConnections.get(i).getWieght());
             double calculation=this.inputConnections.get(i).getWieght() + (this.inputConnections.get(i).getFromNeuron().getOutput()*this.inputConnections.get(i).getToNeuron().getError()*learningRate);
            //double calculation = this.inputConnections.get(i).getWieght() + (this.inputConnections.get(i).getFromNeuron().getOutput() * this.inputConnections.get(i).getToNeuron().getError() * learningRate) + this.alpha * this.deltaW;
            //this.deltaW = (this.inputConnections.get(i).getFromNeuron().getOutput() * this.inputConnections.get(i).getToNeuron().getError() * learningRate) + this.alpha * this.deltaW;
            this.inputConnections.get(i).setWieght(calculation);//wieght= wieght + learningRate*error*ouput/input
            //System.out.println(this.inputConnections.get(i).getName()+"->\t"+this.inputConnections.get(i).getWieght());
        }

    }

    public void calculateErrorHiddenLayer() {//errors backpropgate
        double calculation = 0;
        for (int i = 0; i < this.outputConnections.size(); i++) {//summation(wieght*error)
            calculation += this.outputConnections.get(i).getToNeuron().getError() * this.outputConnections.get(i).getWieght();//
        }
        calculation = this.output * (1 - this.output) * calculation;//summation(wieght*error)*output*(1-output)
        //System.out.println(this.id+"-error->"+calculation);
        this.error = calculation;

    }

    public void updateWieghtHiddenLayers(double learningRate) {//update hidden layer edge wieghts

        for (int i = 0; i < this.inputConnections.size(); i++) {

            // System.out.println(this.inputConnections.get(i).getName()+"->\t"+this.inputConnections.get(i).getWieght());
            double calculation=this.inputConnections.get(i).getWieght() + (this.inputConnections.get(i).getFromNeuron().getOutput()*this.inputConnections.get(i).getToNeuron().getError()*learningRate);
//            double calculation = this.inputConnections.get(i).getWieght() + (this.inputConnections.get(i).getFromNeuron().getOutput() * this.inputConnections.get(i).getToNeuron().getError() * learningRate) + this.alpha * this.deltaW;
//            this.deltaW=(this.inputConnections.get(i).getFromNeuron().getOutput()*this.inputConnections.get(i).getToNeuron().getError()*learningRate)+this.alpha*this.deltaW;
            this.inputConnections.get(i).setWieght(calculation);//wieght= wieght + learningRate*error*ouput/input
            //System.out.println(this.inputConnections.get(i).getName()+"->\t"+this.inputConnections.get(i).getWieght());
        }

    }

    public void printId() {//print neuron information
        System.out.println("Neuron number" + this.id);
    }

    public String getId() {//get neuron id
        return this.id;
    }

    public double getError() {//error get
        return this.error;
    }
}
