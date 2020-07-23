package processor.scenarios;

import processor.Matrix;
import processor.MatrixUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static processor.MatrixUtils.printMatrix;
import static processor.MatrixUtils.readMatrixFromConsole;

public class MatrixTranspositionSc implements Scenario {
    private enum Command {
        MAIN_DIAGONAL_TRANSPOSITION(1, "Main diagonal transposition", () -> {
            Matrix<Double> matrix = readMatrixFromConsole();
            System.out.println("The result of transposition relatively main diagonal is:");
            printMatrix(MatrixUtils.mainDiagonalTransposition(matrix));
        }),
        SIDE_DIAGONAL_TRANSPOSITION(2, "Side diagonal transposition", () -> {
            Matrix<Double> matrix = readMatrixFromConsole();
            System.out.println("The result of transposition relatively side diagonal is:");
            printMatrix(MatrixUtils.sideDiagonalTransposition(matrix));
        }),
        VERTICAL_TRANSPOSITION(3, "Vertical transposition", () -> {
            Matrix<Double> matrix = readMatrixFromConsole();
            System.out.println("The result of transposition relatively vertical line is:");
            printMatrix(MatrixUtils.verticalLineTransposition(matrix));
        }),
        HORIZONTAL_TRANSPOSITION(4, "Horizontal transposition", () -> {
            Matrix<Double> matrix = readMatrixFromConsole();
            System.out.println("The result of transposition relatively horizontal line is:");
            printMatrix(MatrixUtils.horizontalLineTransposition(matrix));
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
        System.out.println();
        for (Command command : Command.values()) {
            System.out.println(command.val + ". " + command.description);
        }
        System.out.print("Your choice: > ");
        parsedConsoleCommand().scenario.run();
    }

    /**
     * @return parsed command from input or Command.EXIT by default
     */
    private Command parsedConsoleCommand() {
        Command retCommand = Command.MAIN_DIAGONAL_TRANSPOSITION;
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
