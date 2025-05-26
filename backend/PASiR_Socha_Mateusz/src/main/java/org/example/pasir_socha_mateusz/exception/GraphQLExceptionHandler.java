package org.example.pasir_socha_mateusz.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.List;



@Component
public class GraphQLExceptionHandler implements DataFetcherExceptionResolver {

    @Override
    public @NotNull Mono<List<GraphQLError>> resolveException(@NotNull Throwable ex,@NotNull DataFetchingEnvironment env) {
        if (ex instanceof ConstraintViolationException validationEx) {
            List<GraphQLError> errors = validationEx.getConstraintViolations().stream()
                    .map(violation -> GraphqlErrorBuilder.newError(env)
                            .message("Błąd walidacji " + violation.getMessage())
                            .build())
                    .toList();


            return Mono.just(errors);
        }
        GraphQLError error = GraphqlErrorBuilder.newError(env)
                .message("Wystąpił bład " + ex.getMessage())
                .build();
        return Mono.just(List.of(error));

    }
}
