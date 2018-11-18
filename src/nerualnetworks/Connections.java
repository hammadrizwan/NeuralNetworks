/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nerualnetworks;

/**
 *
 * @author Hammad
 */
public class Connections {
    String Name;
    double wieght;
    Neuron toNeuron;
    Neuron fromNeuron;
    
    public Connections(Neuron toNeuron,Neuron fromNeuron,String Name){
        this.Name=Name;

        this.wieght=(Math.random() * (1-(-0.5))) + (-0.5);//(max-min)+min
        //this.wieght=(Math.random() * (0.5-(-0.5))) + (-0.5);
        System.out.println(Name+"->"+wieght);
        this.toNeuron=toNeuron;
        this.fromNeuron=fromNeuron; 
    }
    public void reset()//reset wieght
    {
        this.wieght=(Math.random() * (0.5-(-0.5))) + (-0.5);
    }
    public String getName() {
        return Name;
}

    public double getWieght() {
        return wieght;
    }

    public void setWieght(double wieght) {
        this.wieght = wieght;
    }

    public Neuron getToNeuron() {
        return toNeuron;
    }

    public void setToNeuron(Neuron toNeuron) {
        this.toNeuron = toNeuron;
    }

    public Neuron getFromNeuron() {
        return fromNeuron;
    }

    public void setFromNeuron(Neuron fromNeuron) {
        this.fromNeuron = fromNeuron;
    }
   
//    @Override
//public String toString() {
//    return this.getToNeuron().getId();
//}
}
