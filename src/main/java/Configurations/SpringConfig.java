package Configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan("Services")
@ComponentScan("mainBot")
@ComponentScan("BotLastPrices")
@ComponentScan("BotPortfolio")
public class SpringConfig {
}
