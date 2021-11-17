package tscalise.cryptographyProject.libraries.hashing;

/**
 * Enumeración que contiene los algoritmos de cifrado que pueden ser usados por la clase ShaHashing.
 * @author Thomas Scalise
 * @version 1.0 (14/11/2021)
 */
public enum HashAlgorithm {
    SHA1("SHA-1"),
    SHA256("SHA-256"),
    SHA512("SHA-512");

    public String algorithmName;

    HashAlgorithm(String algorithmName) {
        this.algorithmName = algorithmName;
    }
}
