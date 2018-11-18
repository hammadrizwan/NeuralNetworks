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
public class HiddenLayers {
    ArrayList<Neuron> Neurons;
    
    public HiddenLayers(){
        this.Neurons=new ArrayList<Neuron>();
    }
    public void insertNode(Neuron neuron){
        this.Neurons.add(neuron);
    }
    public ArrayList<Neuron> getNeuronList(){
        return this.Neurons;
    }
    public Neuron getNeuronAt(int index){
        return this.Neurons.get(index);
    }
    public int LayerSize(){
        return this.Neurons.size();
    }
    
    
}
