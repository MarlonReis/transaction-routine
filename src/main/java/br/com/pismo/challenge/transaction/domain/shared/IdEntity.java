package br.com.pismo.challenge.transaction.domain.shared;

import br.com.pismo.challenge.transaction.domain.exception.IdentityException;

import java.util.UUID;
import java.util.regex.Pattern;

public class IdEntity {
    private final String id;

    private final String REGEX_VALID_ID = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";

    public IdEntity(String id) {
        if (id == null) {
            throw IdentityException.invalidValue("Identity is required!");
        }
        if (!id.matches(REGEX_VALID_ID)) {
            throw IdentityException.invalidValue("Identity is invalid!");
        }

        this.id = id;
    }

    public UUID value() {
        return UUID.fromString(id);
    }
}
