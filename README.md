# Sweepy - Fabric Minecraft Mod

This repository contains a Minecraft Fabric mod project based on the latest official Fabric mod template.

## Overview

This project has been set up with the latest Fabric mod template for Minecraft 1.21.8, providing a complete development environment for creating Minecraft mods using the Fabric modding framework.

## Template Integration

This repository was initialized with the official [Fabric Example Mod](https://github.com/FabricMC/fabric-example-mod) template, which includes:

- **Fabric Loom**: Build system for Fabric mods
- **Example mod structure**: Complete source code example with main and client entrypoints
- **Gradle wrapper**: No need to install Gradle locally
- **GitHub Actions**: Automated build and release workflows
- **Modern Java**: Java 21+ support
- **Latest dependencies**: Minecraft 1.21.8 with Fabric API

## Prerequisites

- **Java 21 or higher**: Required for Minecraft 1.21.8 mod development
- **Git**: For version control
- **IDE**: IntelliJ IDEA (recommended) or Eclipse with appropriate plugins
- **Internet Access**: Required for downloading Minecraft and Fabric dependencies from maven.fabricmc.net

## Getting Started

### 1. Clone and Setup

The repository is already set up with all necessary files. Simply clone and start developing:

```bash
git clone <repository-url>
cd sweepy
```

### 2. First Build (Important)

Before opening in your IDE, run the first build to download dependencies:

```bash
./gradlew build
```

**Note**: If you encounter network issues accessing `maven.fabricmc.net`, ensure you have a stable internet connection and that your firewall/proxy allows access to Fabric's Maven repository.

### 3. Import into IDE

**For IntelliJ IDEA:**
1. Open IntelliJ IDEA
2. Select "Open or Import"
3. Navigate to the project directory and select `build.gradle`
4. Follow the import wizard

**For Eclipse:**
1. Install the Buildship Gradle plugin
2. Import as "Existing Gradle Project"

### 3. Run the Development Environment

To test your mod in a development environment:

```bash
./gradlew runClient
```

This will launch Minecraft with your mod loaded in a development environment.

### 4. Build the Mod

To build your mod into a `.jar` file:

```bash
./gradlew build
```

The built mod will be available in `build/libs/`.

## Project Structure

```
sweepy/
├── src/
│   ├── main/
│   │   ├── java/com/example/           # Main mod code
│   │   └── resources/                  # Mod resources and configuration
│   └── client/
│       ├── java/com/example/           # Client-side only code
│       └── resources/                  # Client-side resources
├── build.gradle                        # Build configuration
├── gradle.properties                   # Project properties and versions
├── settings.gradle                     # Gradle settings
└── .github/workflows/                  # GitHub Actions for CI/CD
```

## Configuration

Key configuration files:

- `gradle.properties`: Contains mod version, Minecraft version, and dependency versions
- `src/main/resources/fabric.mod.json`: Mod metadata and configuration
- `build.gradle`: Build script with dependencies and build logic

## Customization

To customize this template for your mod:

1. **Update `gradle.properties`**:
   - Change `mod_version`, `maven_group`, and `archives_base_name`
   
2. **Update `src/main/resources/fabric.mod.json`**:
   - Change mod ID, name, description, and author information
   
3. **Rename and modify the example classes**:
   - Move from `com.example` package to your own package
   - Implement your mod's functionality

## Development Commands

- `./gradlew runClient` - Run Minecraft client with your mod
- `./gradlew runServer` - Run Minecraft server with your mod
- `./gradlew build` - Build the mod
- `./gradlew clean` - Clean build artifacts
- `./gradlew genSources` - Generate Minecraft source code for reference

## Troubleshooting

### Build Issues

If you encounter build errors:

1. **Network connectivity**: Ensure you can access `maven.fabricmc.net`
2. **Java version**: Verify you're using Java 21 or higher with `java -version`
3. **Clean build**: Try `./gradlew clean build`
4. **Gradle cache**: Clear Gradle cache with `./gradlew clean --refresh-dependencies`

### Common Issues

- **"Plugin fabric-loom not found"**: This usually indicates network issues accessing the Fabric Maven repository
- **Java version errors**: Make sure you have Java 21+ installed and set as your JAVA_HOME
- **IDE import issues**: Try importing the `build.gradle` file directly rather than the project folder

## Resources

- [Fabric Documentation](https://docs.fabricmc.net/)
- [Fabric Discord](https://discord.gg/v6v4pMv)
- [Yarn Mappings](https://linkie.shedaniel.me/mappings) - For understanding Minecraft's code
- [Fabric API Documentation](https://docs.fabricmc.net/reference/latest/)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

The Fabric template this project is based on is available under the CC0 license.