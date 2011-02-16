package client.java;

public class ExistentialError extends Error {
    ExistentialError() {
        super("Object does not exist anymore.");
    }
}
