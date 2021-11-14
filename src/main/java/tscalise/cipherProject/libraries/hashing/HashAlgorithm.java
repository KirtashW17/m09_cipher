package tscalise.cipherProject.libraries.hashing;

/**
 *  TODO DOCUMENTATION
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
