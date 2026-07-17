package mabrizuela;

public class Main {
    public static void main(String[] args) {
        StringEval evaluator = new StringEval();
        
        // Test 1: AA BB CC DEF ^ * AE / + BC -
        // Esperado: AABCDCCDCCDF
        String expr1 = "AA BB CC DEF ^ * AE / + BC -";
        String result1 = evaluator.evaluate(expr1);
        System.out.println("Test 1:");
        System.out.println("  Expresión: " + expr1);
        System.out.println("  Resultado: " + result1);
        System.out.println("  Esperado:  AABCDCCDCCDF");
        System.out.println("  ✓ CORRECTO" + (result1.equals("AABCDCCDCCDF") ? "" : " - ERROR"));
        System.out.println();
        
        // Test 2: HOLA QUE + TAL COMO ^ ESTAS / BIEN * + BIEN -
        // Esperado: HOLAQUELBCILECNOLCOMLCOMO
        String expr2 = "HOLA QUE + TAL COMO ^ ESTAS / BIEN * + BIEN -";
        String result2 = evaluator.evaluate(expr2);
        System.out.println("Test 2:");
        System.out.println("  Expresión: " + expr2);
        System.out.println("  Resultado: " + result2);
        System.out.println("  Esperado:  HOLAQUELBCILECNOLCOMLCOMO");
        System.out.println("  ✓ CORRECTO" + (result2.equals("HOLAQUELBCILECNOLCOMLCOMO") ? "" : " - ERROR"));
        System.out.println();
        
        // Tests individuales de operadores
        System.out.println("=== Tests de Operadores Individuales ===");
        System.out.println();
        
        // Test (+) Concatenación
        String testConcat = "AAAAA BBBBB +";
        String resultConcat = evaluator.evaluate(testConcat);
        System.out.println("(+) Concatenación:");
        System.out.println("  Expresión: " + testConcat);
        System.out.println("  Resultado: " + resultConcat);
        System.out.println("  Esperado:  AAAAABBBBB");
        System.out.println("  ✓ CORRECTO" + (resultConcat.equals("AAAAABBBBB") ? "" : " - ERROR"));
        System.out.println();
        
        // Test (-) Borrado de primera ocurrencia
        String testMinus = "AAAAABBCCBB BB -";
        String resultMinus = evaluator.evaluate(testMinus);
        System.out.println("(-) Borrado de substring (primera ocurrencia):");
        System.out.println("  Expresión: " + testMinus);
        System.out.println("  Resultado: " + resultMinus);
        System.out.println("  Esperado:  AAAAACCBB");
        System.out.println("  ✓ CORRECTO" + (resultMinus.equals("AAAAACCBB") ? "" : " - ERROR"));
        System.out.println();
        
        // Test (*) Intercalado
        String testMult = "AAA BBBBB *";
        String resultMult = evaluator.evaluate(testMult);
        System.out.println("(*) Intercalado de caracteres:");
        System.out.println("  Expresión: " + testMult);
        System.out.println("  Resultado: " + resultMult);
        System.out.println("  Esperado:  ABABABBB");
        System.out.println("  ✓ CORRECTO" + (resultMult.equals("ABABABBB") ? "" : " - ERROR"));
        System.out.println();
        
        // Test (/) Borrado de caracteres
        String testDiv = "AAAAABBBCCCDDDAAA AB /";
        String resultDiv = evaluator.evaluate(testDiv);
        System.out.println("(/) Borrado de caracteres:");
        System.out.println("  Expresión: " + testDiv);
        System.out.println("  Resultado: " + resultDiv);
        System.out.println("  Esperado:  CCCDDD");
        System.out.println("  ✓ CORRECTO" + (resultDiv.equals("CCCDDD") ? "" : " - ERROR"));
        System.out.println();
        
        // Test (^) Intercalado especial
        String testPow = "EE ABCD ^";
        String resultPow = evaluator.evaluate(testPow);
        System.out.println("(^) Intercalado especial (prefijos):");
        System.out.println("  Expresión: " + testPow);
        System.out.println("  Resultado: " + resultPow);
        System.out.println("  Esperado:  EEAEEABEEABCEEABCD");
        System.out.println("  ✓ CORRECTO" + (resultPow.equals("EEAEEABEEABCEEABCD") ? "" : " - ERROR"));
    }
}

