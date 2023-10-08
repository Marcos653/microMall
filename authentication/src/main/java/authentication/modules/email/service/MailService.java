package authentication.modules.email.service;

import authentication.modules.config.infra.MailJetClientProvider;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    @Value("${mail.fromEmail}")
    private String fromEmail;

    @Value("${mail.fromName}")
    private String fromName;

    private final MailJetClientProvider clientProvider;

    private MailjetRequest createEmailRequest(String toEmail, String subject, String content) {
        return new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", fromEmail)
                                        .put("Name", fromName))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", toEmail)))
                                .put(Emailv31.Message.SUBJECT, subject)
                                .put(Emailv31.Message.HTMLPART, content)));
    }

    public void sendEmail(String toEmail, String subject, String content) {
        var request = createEmailRequest(toEmail, subject, content);
        try {
            clientProvider.getClient().post(request);
        } catch (MailjetException e) {
            throw new RuntimeException("Error sending email", e);
        }
    }
}
