package org.hubson404.javadevelopertest.exceptions;

public class CharacterNotFoundException extends RuntimeException {

    public CharacterNotFoundException(String message) {
        super("Could not find character with name: " + message);
    }

}
