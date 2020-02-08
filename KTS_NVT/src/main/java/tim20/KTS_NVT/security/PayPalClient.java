package tim20.KTS_NVT.security;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;

public class PayPalClient {

    /**
     *Set up the PayPal Java SDK environment with PayPal access credentials.
     *This sample uses SandboxEnvironment. In production, use LiveEnvironment.
     */
    private PayPalEnvironment environment = new PayPalEnvironment.Sandbox(
            "AZy7G54GLeTshw95Qw4OIKWgIVptG4dXgVDMDSV-8K-QkPsAHJsc_CvvzZeBcERZQIjdEbgdlL6v2QBu",
            "EMT59K9y-V7cKCmtfkO4zGQhFrmp6YwYte0xNdEkqtCMwV-CK0S_u1YYB9tguIWga7Xw7-hp_GKEmtQc");

    /**
     *PayPal HTTP client instance with environment that has access
     *credentials context. Use to invoke PayPal APIs.
     */
    PayPalHttpClient client = new PayPalHttpClient(environment);

    /**
     *Method to get client object
     *
     *@return PayPalHttpClient client
     */
    public PayPalHttpClient client() {
        return this.client;
    }
}
