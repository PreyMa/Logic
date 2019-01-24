package com.preyma.logic;

import com.preyma.logic.Simulator.*;
import com.preyma.logic.Gates.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("hello world");

        // Scheduler object
        Scheduler sch= new Scheduler();

        // And Gate as DUT
        Component and= new AndGate();

        // Nets
        Net x= new Net( sch );
        Net y= new Net( sch );
        Net z= new Net( sch );

        // Connecting the nets to the gate
        x.connect( and.getInput( 0 ) );
        y.connect( and.getInput( 1 ) );
        z.connect( and.getOutput( 0 )   );

        // Constants as inputs
        ConstantGate inX= new ConstantGate( sch, State.Low );
        ConstantGate inY= new ConstantGate( sch, State.Low );

        // Connecting the constants to the net
        x.connect( inX.getOutput( 0 ) );
        y.connect( inY.getOutput( 0 ) );

        // Initial state of the output
        System.out.println( z.toString() );

        try {

            // 1 && 1 -> 1
            inX.set(State.High);
            inY.set(State.High);
            System.out.println(z.toString());

            // 0 && 1 -> 0
            inX.set(State.Low);
            System.out.println(z.toString());

        } catch( OscillationError e ) {
            System.out.println("Oscillation apparent.");
        }
    }
}
