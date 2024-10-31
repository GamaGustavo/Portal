package ifs.edu.br.portal.util;

import java.text.Normalizer;

public class Utils {
    public static String removerAcentos(String str) {
        // Normaliza a string para a forma decomposta
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        // Remove todos os caracteres não ASCII
        return nfdNormalizedString.replaceAll("[^\\p{ASCII}]", "");
    }
}
