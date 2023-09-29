package br.com.fiap.infra.configuration;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Esta classe tem como objetivo criar uma Hash para as senhas dos usuários do sistema
 *
 * @author Benefrancis
 * @since 1.0
 */
@ApplicationScoped
public class Password {

    /**
     * Criptografa
     *
     * @param pass
     * @return
     */
    public static String encoder(String pass) {

        MessageDigest algorithm = null;
        StringBuilder hexPass = new StringBuilder();
        try {
            algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigestPass[] = algorithm.digest(pass.getBytes("UTF-8"));

            //Transformando em hexadecimal
            for (byte b : messageDigestPass) {
                hexPass.append(String.format("%02X", 0xFF & b));
            }

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Não foi possível utilizar o algoritmo" + algorithm.getAlgorithm() + ":\n" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.err.println("Não foi possível fazer o encoding para UTF-8:\n" + e.getMessage());
        }

        return hexPass.toString();
    }

    /**
     * Compara uma senha com uma senha criptografada
     *
     * @param pass
     * @param hash
     * @return
     */
    public static boolean check(String pass, String hash) {
        return encoder(pass).equals(hash);
    }
}