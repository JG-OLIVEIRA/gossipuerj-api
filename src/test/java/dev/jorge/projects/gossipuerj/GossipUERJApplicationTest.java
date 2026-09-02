package dev.jorge.projects.gossipuerj;

import org.springframework.boot.SpringApplication;
import org.testcontainers.utility.TestcontainersConfiguration;

public class GossipUERJApplicationTest {

	public static void main(String[] args) {
		SpringApplication.from(GossipUERJApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
