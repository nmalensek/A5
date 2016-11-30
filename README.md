1. Test cases from A4 depend on JUnit. Client/server has no external dependencies.

2. Server: run ParkingGarageServer in the server sub-package using the command line arguments [number of spaces in garage] [host] [port]
Gate Client: run GateClient in the client sub-package using the command line arguments [gate name] [host] [port]
Administrator Client: run AdministratorClient in the client sub-package using the command line arguments [host] [port]

3. No usernames/passwords required.

4. Strong: good report variety, tickets track which gate the customer came in and out of. Administrator client detects if a new gate comes online and adds its information to the reports as applicable. Weak: Administrator reporting only analyzes data from gates that are currently online. Java Swing threading could have been managed better so methods are not running in the background when they don't need to be.

5. Patterns used:
Iterator
Broker
Proxy
Controller/Facade
Observer/Model-view-controller

Refactorings used:
Extract Method
Extract Constant
Move Method
Replace Conditional with Polymorphism
