package common;

import com.hegel.core.functions.ExceptionalConsumer;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
public interface ConnectionPool extends Supplier<Connection> {

    @SneakyThrows
    static ConnectionPool create(Supplier<Connection> connectionSupplier) {
        return connectionSupplier::get;
    }

    static ConnectionPool create(Supplier<Connection> connectionSupplier, String pathToInitScript) {
        ConnectionPool connectionPool = create(connectionSupplier);
        connectionPool.executeScript(pathToInitScript);
        return connectionPool;
    }

    @SneakyThrows
    default int[] executeScript(String pathToScript) {

        try (Connection connection = get();
             Statement statement = connection.createStatement();
             Stream<String> lines = Files.lines(Paths.get(pathToScript))) {

            Arrays.stream(
                    lines.collect(Collectors.joining())
                            .split(";"))
                    .forEachOrdered(ExceptionalConsumer.toUncheckedConsumer(statement::addBatch));

            return statement.executeBatch();
        }
    }
}