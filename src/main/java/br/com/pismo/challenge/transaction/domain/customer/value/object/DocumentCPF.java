package br.com.pismo.challenge.transaction.domain.customer.value.object;

import br.com.pismo.challenge.transaction.domain.exception.InvalidDocumentException;

import java.util.regex.Pattern;

public class DocumentCPF {
    private static final Pattern CONTAINS11_DIGITS = Pattern.compile("^\\d{11}$");
    private static final Pattern REPEAT_NUMBERS = Pattern.compile("^(?!.*(\\d)\\1{9}).+");
    private static final Pattern ALL_SIGNALS = Pattern.compile("[-.]");

    private final String value;

    public DocumentCPF(String document) {
        if (document == null) {
            throw new InvalidDocumentException("Document is required!");
        }
        this.value = document.replaceAll(ALL_SIGNALS.pattern(), "");

        if (!CONTAINS11_DIGITS.matcher(this.value).matches()) {
            throw new InvalidDocumentException();
        }

        if (!REPEAT_NUMBERS.matcher(this.value).matches()) {
            throw new InvalidDocumentException();
        }

        if (isInvalid(this.value)) {
            throw new InvalidDocumentException();
        }
    }

    private static boolean isInvalid(String cpf) {
        final String[] digits = cpf.split("");
        int total = sumDigit(digits, 10);
        if (restResponse(total) != Integer.parseInt(digits[9])) {
            return true;
        }

        total = sumDigit(digits, 11);
        return restResponse(total) != Integer.parseInt(digits[10]);
    }

    private static int restResponse(int total) {
        final int result = (total * 10) % 11;
        if (result >= 10) {
            return 0;
        }
        return result;
    }


    private static int sumDigit(String[] digits, int positions) {
        int total = 0;
        for (int p = 0; p < (positions - 1); p++) {
            total += Integer.parseInt(digits[p]) * (positions - p);
        }
        return total;
    }


    public String value() {
        return value;
    }


}