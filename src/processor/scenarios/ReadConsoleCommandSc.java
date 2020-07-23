package processor.scenarios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadConsoleCommandSc implements Scenario {
    private enum Command {
        ADD_MATRICES(1, "Add matrices", new AddMatricesSc()),
        MULTIPLY_MATRIX_TO_A_CONSTANT(2,
                                      "Multiply matrix to a constant",
                                      new MatrixMultiplicationToAConstantSc()),
        MULTIPLY_MATRICES(3, "Multiply matrices", new MatricesMultiplicationSc()),
        TRANSPOSE_MATRIX(4, "Transpose matrix", new MatrixTranspositionSc()),
        CALCULATE_DETERMINANT(5, "Calculate a determinant", new DeterminantSc()),
        INVERSE_MATRIX(6, "Inverse matrix", new MatrixInversionSc()),
        EXIT(0, "Exit", () -> {
            System.out.println("Exiting");
        });

        final int val;
        private final String description;
        private final Scenario scenario;

        Command(int val, String description, Scenario commandScenario) {
            this.val = val;
            this.description = description;
            scenario = commandScenario;
        }

        @Override
        public String toString() {
            return val + ". " + description;
        }
    }

    @Override
    public void run() {
        Command currentCommand;
        do {
            for (Command command : Command.values()) {
                System.out.println(command.val + ". " + command.description);
            }
            System.out.print("Your choice: > ");
            currentCommand = parsedConsoleCommand();
            currentCommand.scenario.run();
        }
        while (!currentCommand.equals(Command.EXIT));
    }

    /**
     * @return parsed command from console input or Command.EXIT by default
     */
    private Command parsedConsoleCommand() {
        Command retCommand = Command.EXIT;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int commandValue = 0;
        try {
            commandValue = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Command command : Command.values()) {
            if (command.val == commandValue) {
                retCommand = command;
                break;
            }
        }
        return retCommand;
    }
}