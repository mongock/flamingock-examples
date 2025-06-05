package io.flamingock;

import io.flamingock.core.api.metadata.FlamingockMetadata;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Optional<FlamingockMetadata> instance = FlamingockMetadata.getInstance();
        FlamingockMetadata metadata = instance.orElseThrow(() -> new RuntimeException("No flamingock metadata"));
        System.out.println(metadata);
    }
}