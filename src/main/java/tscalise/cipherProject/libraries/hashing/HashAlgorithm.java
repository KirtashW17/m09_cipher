package tscalise.cipherProject.libraries.hashing;

/**
 *  TODO DOCUMENTATION
 */
public enum HashAlgorithm {
    SHA1("SHA-1", 20),
    SHA256("SHA-256", 32),
    SHA512("SHA-512", 64);

    public final int bytes;
    public String label;

    HashAlgorithm(String label, int bytes) {
        this.bytes = bytes;
        this.label = label;
    }


}
