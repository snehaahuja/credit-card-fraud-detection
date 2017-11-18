package com.ccoew.finalproject;
import java.util.ArrayList;
import java.util.Random;
import java.text.DecimalFormat;

public class BackPropogation
{
	 ArrayList <DS_Definition> inputTemp = new ArrayList<DS_Definition>();
	 ArrayList <ArrayList> inputOut = new ArrayList<ArrayList>();
	
	
	     
	 public final double ALPHA = 0.01;     // Learning rate (alpha), which limits
                                                  // the size of a weight/bias adjustment
                                                  // step in a single training iteration.
                                                  // The smaller the value of alpha, the
                                                  // longer a network will take to train.

	 public final double MU = 0.7;         // Momentum value (mu). This enables the
                                                  // weight to change in response to the
                                                  // current gradient step and also to the
                                                  // previous one. It allows the network to
                                                  // find a reasonable solution in fewer
                                                  // training iterations. When both the
                                                  // current step and previous step are in
                                                  // agreement, it allows for a larger step size. 

	 public final int N = 5;               // The number neurons in the input
                                                  // layer equals the number of variables
                                                  // inherent to the problem.
	 public final int M = 5;
	 public final int NUM_CASES = 1999;
	 public final int TEST_CASES = 3;
    
	 public double hbias[] = null;          // 'h' refers to the hidden layer.
	 public double hweight[][] = null;
	 public double obias = 0.0;             // 'o' refers to the output layer.
	 public double oweight[] = null;
    
	 public double hbias2[] = null;         // '2' refers to training copies.
	 public double hweight2[][] = null;
	 public double obias2 = 0.0;
	 public double oweight2[] = null;
    
	 public double hin[] = null;
    public double hout[] = null;
    
    public double oin = 0.0;
    public double oout = 0.0;           

    
    
    public double inputNeuron[] = null;
    
    public void initialize(final int n, final int m,ArrayList <DS_Definition> ds)
    {
    	//System.out.println("HI WE ARE IN INITIALISE!!");
    	//FileRead fr=new FileRead();
    	//StudentAdmissionController sc=new StudentAdmissionController();
		inputTemp=ds;
		//System.out.println(inputTemp.size());
		for(int l=0; l<inputTemp.size(); l++){
			ArrayList <Double> inputIn = new ArrayList<Double>();
			inputIn.add((double) inputTemp.get(l).seventyCross);
			inputIn.add((double) inputTemp.get(l).location);
			inputIn.add((double) inputTemp.get(l).period);
			inputIn.add((double) inputTemp.get(l).dist);
			inputIn.add((double) inputTemp.get(l).time);
			//inputIn.add((double) inputTemp.get(l).addChange);

			inputIn.add((double) inputTemp.get(l).output);
			
			
			inputOut.add(inputIn);
		}
    	
		System.out.println(inputOut.size());
    	for(int f=0; f<inputOut.size(); f++)
    	{
    		System.out.println(inputOut.get(f).get(0)+" "+inputOut.get(f).get(1)+" "+inputOut.get(f).get(2)+" "+inputOut.get(f).get(3)+" "+inputOut.get(f).get(4)+" "+inputOut.get(f).get(5)+" "/*+inputOut.get(f).get(6)*/);
    	}
    	
    	
    	
    	hbias = new double[M];
        hweight = new double[N][M];
        oweight = new double[M];
        hbias2 = new double[M];
        hweight2 = new double[N][M];
        oweight2 = new double[M];
        hin = new double[M];
        hout = new double[M];
        inputNeuron = new double[N];
        
        // Since there's no way of knowing the appropriate weights and
        // biases prior to training, randomly initialize each neuron
        // with a weight between –0.5 and 0.5
        
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < m; j++)
            {
                hweight[i][j] = new Random().nextDouble() - 0.5;   // hidden layer connections.
                hweight2[i][j] = hweight[i][j];                    // copies for training.
            } // j
        } // i
        
        for(int j = 0; j < m; j++)
        {
            hbias[j] = new Random().nextDouble() - 0.5;            // hidden layer biases.
            hbias2[j] = hbias[j];                                  // copies for training.
            oweight[j] = new Random().nextDouble() - 0.5;          // output layer connections.
            oweight2[j] = oweight[j];                              // copies for training.
        } // j
        
        obias = new Random().nextDouble() - 0.5;                   // output neuron bias
        obias2 = obias;                                            // copy for training.
        return;
    }
    
    public void trainNetwork(ArrayList <Def> trainOutput)
    {
        double trainError = 0.0;
        int j = 0;
        int trainingCycles = 0;
        boolean trainingComplete = false;
        DecimalFormat dfm = new java.text.DecimalFormat("###0.000");
    
        while(j <100)
        {
            trainError = 0;
            for(int i = 0; i < NUM_CASES; i++)
            {
                
                for(int k = 0; k < N; k++)
                {
                    inputNeuron[k] = Double.parseDouble((inputOut.get(i).get(k)).toString());
                   
                } // k
                hiddenInput(N, M);
                outputInput(M);
                oout = sigmoid(oin);
                backPropagate(i, M);
                
                    
                        //System.out.println(j + "  " + i + "  Expected: " + inputOut.get(i).get(5) + "  Actual: " + dfm.format(oout));
                    
                        Def d=new Def(Double.parseDouble(inputOut.get(i).get(5).toString()),oout,j,i);
                        trainOutput.add(d);
                
                trainError += Math.pow(((Double.parseDouble((inputOut.get(i).get(5)).toString())) - oout), 2);
            } // i
            
            /*for(int x=0;x<trainOutput.size();x++)
            {
            	System.out.println(trainOutput.get(x).epoch+" "+trainOutput.get(x).instanceNum+" "+"Expected "+trainOutput.get(x).in+"  Actual "+trainOutput.get(x).out);
            }*/
            trainError = Math.sqrt(trainError / NUM_CASES);
            
            // Display training error level every thousand epochs.
                    System.out.println("Training Error: " + dfm.format(trainError) + "\n");
            
        
            // The net trains through 10000 epochs anyway, but it's interesting to see where the
            // training error became satisfactory.
            if(trainError < 0.01){
                if(trainingComplete == false){
                    trainingCycles = j;
                    trainingComplete = true;
                }
            }
            
            j++;
        }
    
        System.out.println("Training iterations needed: " + trainingCycles);
        
        return;
    }

    public void hiddenInput(final int n, final int m)
    {
        // This carries out the summation process for each neuron in the
        // hidden layer of the neural network.
        // The input value to a node is:
        // the bias for that node
        // added to the sum of each input interconnection,
        // where the value of an interconnection is the input neuron's value
        // multiplied by the weight of the connection.

        double sum = 0.0;
        
        for(int j = 0; j < m; j++)
        {
            sum = 0;
            for(int i = 0; i < n; i++)
            {
                sum += (inputNeuron[i] * hweight[i][j]);
            } // i
            hin[j] = hbias[j] + sum;
        } // j
        
        // Send each scaled input of the hidden layer through the bipolar sigmoid (trans) function.
        for(int j = 0; j < m; j++)
        {
            hout[j] = sigmoid(hin[j]);
        } // j
        
        return;
    }

    public void outputInput(final int m)
    {
        // Pass hidden layer values through the weighted connections to the output layer.
        double sum = 0.0;
        
        for(int j = 0; j < m; j++)
        {
            sum += (hout[j] * oweight[j]);
        } // j
        oin = obias + sum;
        
        return;
    }

    public void backPropagate(final int i, final int m)
    {
        // Back-propagates the error to the interconnections between the output and hidden layer neurons.
        double odelta = sigmoidDerivative(oin) * ((Double.parseDouble((inputOut.get(i).get(5)).toString())) - oout);
        double dobias = 0.0;
        double doweight[] = new double[m];
        
        for(int j = 0; j < m; j++)
        {
            doweight[j] = (ALPHA * odelta * hout[j]) + (MU * (oweight[j] - oweight2[j]));
            oweight2[j] = oweight[j];
            oweight[j] += doweight[j];
        } // j
        
        dobias = (ALPHA * odelta) + (MU * (obias - obias2));
        obias2 = obias;
        obias += dobias;
        
        updateHidden(N, m, odelta);
        
        return;
    }

    public void updateHidden(final int n, final int m, final double d)
    {
        // Since there can be multiple hidden layer neurons as well as multiple
// input neurons, some additional loops are utilized to ensure that every
        // weight and bias is updated appropriately.
        double hdelta = 0.0;
        double dhbias[] = new double[m];
        double dhweight[][] = new double[n][m];
        
        for(int j = 0; j < m; j++)
        {
            hdelta = (d * oweight[j]) * sigmoidDerivative(hin[j]);
        
            for(int i = 0; i < n; i++)
            {
                dhweight[i][j] = (ALPHA * hdelta * inputNeuron[i]) + (MU * (hweight[i][j] - hweight2[i][j]));
                hweight2[i][j] = hweight[i][j];
                hweight[i][j] += dhweight[i][j];
            } // i
        
            dhbias[j] = (ALPHA * hdelta) + (MU * (hbias[j] - hbias2[j]));
            hbias2[j] = hbias[j];
            hbias[j] += dhbias[j];
        } // j
        
        return;
    }

    public double examineTransaction(double INPUTS[])
    {
        inputNeuron[0] = INPUTS[0];  
        inputNeuron[1] = INPUTS[1];  
        inputNeuron[2] = INPUTS[2];  
        inputNeuron[3]=INPUTS[3];
        inputNeuron[4]=INPUTS[4];
        //inputNeuron[5]=1;
        
        double oout1;
        
        System.out.println("M "+M+"N "+N+"oin "+oin);
        
        for(int r=0;r<5;r++)
        {
        	//System.out.println(r+" "+INPUTS[r]);
        }
        
        hiddenInput(N, M);
        outputInput(M);
        oout1 = sigmoid(oin);
        
        
        
        /*System.out.println("Predicted value: "+oout1);
        if(oout1 > 0){
            System.out.println("\nThis transaction is probably fraudulent\n");
        }else{
            System.out.println("\nThis transaction not likely to be fraudulent\n");
        }*/
        return oout1;
    }



    public double sigmoid(final double val)
    {
        // A.K.A. the activation function, it takes the sum of all the neurons'
        // weighted inputs and uses the value to calculate the neurons' output.
        // This output is also thought of as the "excitation" of the neuron.
        
        return (2.0 / (1.0 + (Math.exp(-val)))) - 1.0; // Output ranges from -1 to 1.
    }

    public double sigmoidDerivative(final double val)
    {
        // Backpropagation:
        // Calculate delta by multiplying the difference between:
        // an output computed by the neural network for a given pattern,
        // and a target value for a known outcome for the given pattern,
        // by the derivative of the activation function (Sigmoid).
        
        return ((2*Math.exp(val))/((1+Math.exp(val))*(1+Math.exp(val))));
    }
    
    /*public void main(String[] args)
    {
        initialize(N, M);
        trainNetwork();
        examinePatient();
       // validateNet();
        return;
    }*/

}
