# Car Sharing
Application responsible for managing car rental activities.
Application has 2 main UI sections:
- **for managers**: create companies, create cars
- **for customers**: rent a car, return a car, see current rented car
- additionally in main menu you can create customers
In learning purposes CLI used as UI.

### Main Business Features:
1. Rent a car
2. Return rented car
3. See current rented car
4. Managers admin panel to add cars and companies

### Installing
To install app you need:
- Install jdk 17

### Build
- To create JAR artifact use command `./gradlew clean shadowJar`

### Configuration parameters
- Set starting program arguments `-databaseFileName [name]`, where `[name]` is any valid DB name

### Run with IDE (__Intellij IDEA__)
1. Open the project in IDE
2. IDE Menu -> Run -> Edit configuration -> Program arguments -> set parameters (see Configuration parameters section)
3. Go to AppRunner class -> Run AppRunner.main(String[]) method
4. New DB will be created automatically (if not existed) with name provided from step 2
5. Main menu appears on the screen
6. Choose a command by entering menu number

### Run with terminal
run artifact by `jar` command.
when starting artifact pass parameters (see Configuration parameters section)
Example: `Example: `java -jar build/libs/Car-Sharing-1.0-SNAPSHOT.jar -databaseFileName database``




### ToDo
- coming soon

