package authentication.modules.config.infra;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailJetClientProvider {

    @Value("${mailjet.apiKey}")
    private String apiKey;

    @Value("${mailjet.apiSecretKey}")
    private String apiSecretKey;

    public MailjetClient getClient() {
        var options = ClientOptions.builder()
                .apiKey(apiKey)
                .apiSecretKey(apiSecretKey)
                .build();
        return new MailjetClient(options);
    }
}
