import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.1"
	id("io.spring.dependency-management") version "1.1.0"
	id("com.google.cloud.tools.jib") version "3.3.2"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.jpa") version "1.8.22"
}

group = "com.olacodes"
version = "0.0.1-SNAPSHOT"

extra["springCloudVersion"] = "2022.0.3"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}


dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.1.1")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	// Security
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// Cloud
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")


	// Object Mapper
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.modelmapper:modelmapper:3.1.1")

	// Database
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.flywaydb:flyway-core")
	runtimeOnly("org.postgresql:postgresql")

	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jib {
	from {
		platforms {
			platform {
				os = "linux"
				architecture = "amd64"
			}
		}
	}
	to {
		image = "olacodes/auth-service"
	}
}