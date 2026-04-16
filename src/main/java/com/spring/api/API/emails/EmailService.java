package com.spring.api.API.emails;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage eMessage = new SimpleMailMessage();
        eMessage.setTo(to);
        eMessage.setSubject(subject);
        eMessage.setText(text);
        mailSender.send(eMessage);
    }

    public void sendHTMLEmail(String to, String subject, String token) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(this.HtmlContent(token), true);
        mailSender.send(mimeMessage);
    }

    public String HtmlContent(String token){
        return """
        <!DOCTYPE html>
        <html lang="es">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Confirma tu cuenta</title>
            <style>
                /* Reset styles */
                * {
                    margin: 0;
                    padding: 0;
                    box-sizing: border-box;
                }

                /* Base styles */
                body {
                    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, sans-serif;
                    line-height: 1.6;
                    color: #333333;
                    background-color: #f5f5f5;
                }

                /* Container */
                .email-container {
                    max-width: 600px;
                    margin: 0 auto;
                    background-color: #ffffff;
                    border-radius: 8px;
                    overflow: hidden;
                    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
                }

                /* Header */
                .header {
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    padding: 40px 30px;
                    text-align: center;
                    color: white;
                }

                .logo {
                    font-size: 32px;
                    font-weight: bold;
                    margin-bottom: 10px;
                    text-decoration: none;
                    color: white;
                }

                .header-subtitle {
                    font-size: 16px;
                    opacity: 0.9;
                    margin: 0;
                }

                /* Content */
                .content {
                    padding: 40px 30px;
                }

                .welcome-title {
                    font-size: 28px;
                    font-weight: 600;
                    color: #2d3748;
                    margin-bottom: 20px;
                    text-align: center;
                }

                .welcome-text {
                    font-size: 16px;
                    color: #4a5568;
                    margin-bottom: 30px;
                    text-align: center;
                    line-height: 1.8;
                }

                /* Button */
                .button-container {
                    text-align: center;
                    margin: 40px 0;
                }

                .confirm-button {
                    display: inline-block;
                    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                    color: white;
                    text-decoration: none;
                    padding: 16px 32px;
                    border-radius: 8px;
                    font-weight: 600;
                    font-size: 16px;
                    transition: all 0.3s ease;
                    box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
                }

                .confirm-button:hover {
                    transform: translateY(-2px);
                    box-shadow: 0 8px 25px rgba(102, 126, 234, 0.6);
                }

                /* Alternative link */
                .alternative-section {
                    background-color: #f8fafc;
                    padding: 25px;
                    border-radius: 8px;
                    margin: 30px 0;
                    border-left: 4px solid #667eea;
                }

                .alternative-title {
                    font-size: 14px;
                    font-weight: 600;
                    color: #2d3748;
                    margin-bottom: 10px;
                }

                .alternative-link {
                    font-size: 12px;
                    color: #4a5568;
                    word-break: break-all;
                    background-color: #edf2f7;
                    padding: 10px;
                    border-radius: 4px;
                    font-family: 'Courier New', monospace;
                }

                /* Security info */
                .security-info {
                    background-color: #fef5e7;
                    border: 1px solid #f6ad55;
                    border-radius: 8px;
                    padding: 20px;
                    margin: 30px 0;
                }

                .security-icon {
                    font-size: 24px;
                    margin-bottom: 10px;
                }

                .security-title {
                    font-size: 14px;
                    font-weight: 600;
                    color: #744210;
                    margin-bottom: 8px;
                }

                .security-text {
                    font-size: 13px;
                    color: #744210;
                    line-height: 1.5;
                }

                /* Expiry notice */
                .expiry-notice {
                    text-align: center;
                    margin: 30px 0;
                    padding: 15px;
                    background-color: #fed7d7;
                    border-radius: 6px;
                    border-left: 4px solid #e53e3e;
                }

                .expiry-text {
                    font-size: 14px;
                    color: #742a2a;
                    font-weight: 500;
                }

                /* Footer */
                .footer {
                    background-color: #2d3748;
                    color: #a0aec0;
                    padding: 30px;
                    text-align: center;
                }

                .footer-text {
                    font-size: 14px;
                    margin-bottom: 15px;
                    line-height: 1.5;
                }

                .footer-links {
                    margin: 20px 0;
                }

                .footer-link {
                    color: #667eea;
                    text-decoration: none;
                    margin: 0 15px;
                    font-size: 13px;
                }

                .footer-link:hover {
                    text-decoration: underline;
                }

                .company-info {
                    font-size: 12px;
                    color: #718096;
                    margin-top: 20px;
                    line-height: 1.4;
                }

                /* Responsive */
                @media only screen and (max-width: 600px) {
                    .email-container {
                        margin: 0;
                        border-radius: 0;
                    }

                    .header,
                    .content,
                    .footer {
                        padding: 30px 20px;
                    }

                    .welcome-title {
                        font-size: 24px;
                    }

                    .confirm-button {
                        padding: 14px 28px;
                        font-size: 15px;
                    }

                    .alternative-section {
                        padding: 20px 15px;
                    }
                }

                /* Dark mode support */
                @media (prefers-color-scheme: dark) {
                    .email-container {
                        background-color: #1a202c;
                    }

                    .content {
                        background-color: #1a202c;
                    }

                    .welcome-title {
                        color: #e2e8f0;
                    }

                    .welcome-text {
                        color: #cbd5e0;
                    }

                    .alternative-section {
                        background-color: #2d3748;
                    }

                    .alternative-title {
                        color: #e2e8f0;
                    }

                    .alternative-link {
                        background-color: #4a5568;
                        color: #e2e8f0;
                    }
                }
            </style>
        </head>

        <body>
            <div class="email-container">
                <!-- Header -->
                <div class="header">
                    <div class="logo">TuApp</div>
                    <p class="header-subtitle">Bienvenido a nuestra plataforma</p>
                </div>

                <!-- Content -->
                <div class="content">
                    <h1 class="welcome-title">¡Confirma tu cuenta!</h1>

                    <p class="welcome-text">
                        Hola ,<br><br>
                        Gracias por registrarte. Para completar tu registro y acceder a todas las funcionalidades,
                        necesitas confirmar tu dirección de correo electrónico.
                    </p>

                    <!-- Button -->
                    <div class="button-container">
                        <a href="{{enlace_confirmacion}}" class="confirm-button">Confirmar cuenta</a>
                    </div>

                    <!-- Alternative link -->
                    <div class="alternative-section">
                        <div class="alternative-title">O usa este código de confirmación:</div>
                        <div class="alternative-link"> {{TOKEN}} </div>
                    </div>

                    <!-- Security info -->
                    <div class="security-info">
                        <div class="security-icon">🔒</div>
                        <div class="security-title">Información de seguridad</div>
                        <div class="security-text">
                            Si no creaste esta cuenta, puedes ignorar este email de forma segura.
                            Tu información no será utilizada sin tu confirmación.
                        </div>
                    </div>

                    <!-- Expiry notice -->
                    <div class="expiry-notice">
                        <div class="expiry-text">
                            ⏰ Este enlace expira en <strong>24 horas</strong>
                        </div>
                    </div>
                </div>

                <!-- Footer -->
                <div class="footer">
                    <p class="footer-text">
                        Este es un email automático, por favor no respondas a esta dirección.
                    </p>

                    <div class="footer-links">
                        <a href="{{enlace_ayuda}}" class="footer-link">Centro de ayuda</a>
                        <a href="{{enlace_contacto}}" class="footer-link">Contacto</a>
                        <a href="{{enlace_privacidad}}" class="footer-link">Privacidad</a>
                    </div>

                    <div class="company-info">
                        © 2024 TuApp. Todos los derechos reservados.<br>
                        Calle Principal 123, Ciudad, País<br>
                        <a href="{{enlace_unsubscribe}}" style="color: #667eea;">Darse de baja</a>
                    </div>
                </div>
            </div>
        </body>

        </html>        
        """.replace("{{TOKEN}}", token);
    }
}
