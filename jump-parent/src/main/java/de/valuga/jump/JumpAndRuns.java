package de.valuga.jump;
import lombok.Getter;

/**
 * @author Nico_ND1
 */
public class JumpAndRuns {

    @Getter private static JumpAndRunOperator operator;

    public static void setOperator(JumpAndRunOperator operator) {
        if (JumpAndRuns.operator != null) throw new UnsupportedOperationException("Cant change singleton");

        JumpAndRuns.operator = operator;
    }

}
