package net.franzk.newsletter.controller;

import jakarta.validation.Valid;
import net.franzk.newsletter.domain.Subscription;
import net.franzk.newsletter.exception.EmailAlreadySubscribedException;
import net.franzk.newsletter.exception.NoSuchEmailSubscribedException;
import net.franzk.newsletter.service.NewsletterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/newsletter")
public class NewsletterController {
    private final NewsletterService newsletterService;

    public NewsletterController(NewsletterService newsletterService) {
        this.newsletterService = newsletterService;
    }

    /**
     * Subscribe to the newsletter
     *
     * @param subscriptionDto the email to subscribe
     * @return success message
     * @throws EmailAlreadySubscribedException if the email is already subscribed
     */
    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestBody @Valid SubscriptionDto subscriptionDto) throws EmailAlreadySubscribedException {
        Subscription subscription = newsletterService.subscribe(subscriptionDto.getEmail());
        return ResponseEntity.ok("Subscribed to newsletter " + subscription.getEmail());
    }

    /**
     * Unsubscribe from the newsletter
     *
     * @param subscriptionDto the email to unsubscribe
     * @return success message
     * @throws NoSuchEmailSubscribedException if the email is not subscribed
     */
    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestBody @Valid SubscriptionDto subscriptionDto) throws NoSuchEmailSubscribedException {
        newsletterService.unsubscribe(subscriptionDto.getEmail());
        return ResponseEntity.ok("Unsubscribed from newsletter " + subscriptionDto.getEmail());
    }

    /**
     * Get all subscriptions
     *
     * @return list of subscriptions
     */
    @GetMapping("/mailing-list")
    public ResponseEntity<List<SubscriptionDto>> getSubscriptions() {
        List<SubscriptionDto> subscriptions = newsletterService.getSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

}
