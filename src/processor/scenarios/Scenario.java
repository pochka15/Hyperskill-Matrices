package processor.scenarios;

/**
 * A scenario that can be run for a specific command
 */
@FunctionalInterface
public interface Scenario {
    void run();
}
