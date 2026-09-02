package dev.jorge.projects.gossipuerj;

import dev.jorge.projects.gossipuerj.config.TestcontainersConfiguration;
import org.springframework.boot.SpringApplication;

public class GossipUERJApplicationTest {

	public static void main(String[] args) {
		SpringApplication.from(GossipUERJApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
