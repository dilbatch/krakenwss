package org.example.populator;

public abstract class JsonDataPopulator {
    public abstract <S, T> void populate(S source, T target);
}
