[common-web-architecture]: common-web-architecture.png "Common web architectures"
[canonical-backend-reference]: canonical-backend-reference.png "Canonical backend reference"
[building-block]: building-block.png "Building-block implementation"
[dependency-graph]: dependency-graph.png "Dependency graph of canonical-backend-reference"

# Architectural building blocks

## Web application backend architecture

There are currently two main architectural styles for the architecture of a web application backend.
On the left side there is an example shown for a more traditional [layered architecture]( https://www.petrikainulainen.net/software-development/design/understanding-spring-web-application-architecture-the-classic-way/).
The [onion](http://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) (or hexagonal) architecture on the right shares the main elements with the layered one.
The biggest difference is, that whilst in the layered architecture the domain depends on the data access layer, in the onion architecture it is the other way around.

![common-web-architecture]

## Canonical backend architecture

Both styles share some common elements such as adapter/controller, (application) services and domain modules.
In addition to the common elements there are also common rules, like services are not allowed to access controllers and entities are not allowed to access services.

As an exemplary project, the `canonical-backend-architecture` project in this repository uses those common elements and can be used for testing the building blocks.
This screenshot shows its structure:

![canonical-backend-reference]

## Reusable building blocks

In the scope of this [Bachelor's thesis](http://dbis.eprints.uni-ulm.de/1738/), we created reusable architectural building blocks, which represent the most common elements. 
Instead of having to define building blocks anew for every project, these predefined building blocks can be reused for every project with a similar architecture as the sample project.
Even with added or missing parts in your project, the building blocks can be extended and adjusted easily to fit your needs.

Building blocks have properties that define rules, such as allowed relations to other building blocks or allowed Java annotations (to for example restrict classes annotated with `@Controller` to HTTP controller building blocks).
Here is an example of an implemented building block:
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@BuildingBlock(uses = {StoreBuildingBlock.class, DomainModuleBuildingBlock.class,
	InfrastructureBuildingBlock.class, HttpDtoModelBuildingBlock.class, JmsEventModelBuildingBlock.class})
public @interface ApplicationServiceBuildingBlock {

	Class<?>[] allowedAnnotations() default {};

	boolean includeSubPackages() default true;
}
```

Every building block is annotated with `@BuildingBlock`.
In that annotation, the previously mentioned properties are set.
Again, these can be extended to allow for additional properties, by changing the implementation of the annotation. 

### Automatic validation with jQAssistant

[jQAssistant](https://jqassistant.org/) is a QA Tool for creating and validating software architecture rules.
It analyses the Java bytecode and stores all information about the structure of the code in a Neo4j database.
By writing concepts and constraints in Neo4j's query language Cypher, it is possible to enrich the database and then to define the rules that should be enforced.
Both are executed during the build process to validate the project.

In this project, we used the concepts to label every building block accordingly, as well as their relationships to each other (e.g. A uses B). 
This provides the information for our constraints to check for derivations from the desired architecture.
Additionally it is also checked that there are no cyclic dependencies.

## How to use sample project

Ideally, if you use the building-blocks correctly, you will end up with a dependency graph like this one:
![dependency-graph]
The green relations are allowed building block usages.
Whilst the blue ones represent actual code dependencies.

To get there, check out the project in your favoured IDE with Java 11.
From your parent module, execute first `mvn clean install` and then `mvn jqassistant:server`.
The last command will run the integrated Neo4j server, which you can reach under http://localhost:7474.
There you can play around with different queries to explore the `canonical-backend-architecture`.
Refer to [Neo4j's Cypher Basics](https://neo4j.com/developer/cypher-query-language/) for writing your own queries.
  
## Usage of the building block annotations in your own project

Add `building-blocks-annotation` as a Maven dependency to your project.
```xml
<dependency>
	<groupId>com.transporeon.buildingblocks.annotation</groupId>
	<artifactId>building-blocks-annotation</artifactId>
	<version>1.0</version>
</dependency>
```

Make sure to keep the mentioned structure: 
Add the `jqassistant` folder with the `building-blocks-rules.xml` file to your project and add `@PredefinedBuildingBlock` annotated classes
to every root package that represents a building block (for a detailed example, have a look at the `canonical-backend-reference` project). 
Only with the added building blocks can the concepts and constraints be applied.
The attribute `includeSubPackages` can be used to declare a building block which includes all classes of its sub-packages.
Also add the following to you `pom.xml`:
```xml
<build>
	<plugins>
		<plugin>
			<groupId>com.buschmais.jqassistant</groupId>
			<artifactId>jqassistant-maven-plugin</artifactId>
			<version>1.6.0</version>
			<executions>
				<execution>
					<goals>
						<goal>scan</goal>
						<goal>analyze</goal>
					</goals>
					<configuration>
						<concepts>
							<concept>classpath:Resolve</concept>
						</concepts>
						<scanIncludes>
							<scanInclude>
								<!-- NOTE Currently that has to point to the directory of the Maven project. -->
								<path>../building-blocks-annotation/target/classes</path>
								<scope>java:classpath</scope>
							</scanInclude>
						</scanIncludes>
						<warnOnSeverity>MINOR</warnOnSeverity>
						<failOnSeverity>MAJOR</failOnSeverity>
						<groups>
							<group>static</group>
							<group>green</group>
							<group>blue</group>
							<group>responsibilities</group>
							<group>constraintElement</group>
							<group>constraintImperative</group>
							<group>constraintProhibition</group>
						</groups>
					</configuration>
				</execution>
			</executions>
		</plugin>
	</plugins>
</build>

<reporting>
	<plugins>
		<plugin>
			<groupId>com.buschmais.jqassistant</groupId>
			<artifactId>jqassistant-maven-plugin</artifactId>
			<reportSets>
				<reportSet>
					<reports>
						<report>report</report>
					</reports>
				</reportSet>
			</reportSets>
		</plugin>
	</plugins>
</reporting>
```
Now your project is ready.
If you want to add rules, you can do so by implementing them in the predefined building blocks and writing concepts/constraints to the `building-blocks-rules.xml`.