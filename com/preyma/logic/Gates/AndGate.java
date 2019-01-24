package com.preyma.logic.Gates;

import com.preyma.logic.Simulator.State;
import com.preyma.logic.Simulator.Component;

public class AndGate extends Component {

    public AndGate() {
        // 2 Inputs & 1 Output
        super( 2, 1 );
    }

    public void update() {
        State a=   getInput( 0 ).getValue();
        State b=   getInput( 1 ).getValue();

        if( a.is( State.Error ) || b.is( State.Error ) || ( a.is( State.Undefined ) && b.is( State.Undefined ) ) ) {
            getOutput().output( State.Error, 1 );

        } else if( ( a.isHigh() || a.is( State.Undefined) ) &&
                ( b.isHigh() || b.is( State.Undefined) ) ) {
            getOutput().output( State.High, 1 );

        } else {
            getOutput().output( State.Low, 1 );
        }
    }

}
