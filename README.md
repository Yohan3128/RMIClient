# Remote Method Invocation (RMI) Client - Practical

## Overview

Remote Method Invocation (RMI) is a Java technology that enables an object running in one JVM to invoke methods on an object running in another JVM. It is used to develop distributed applications and supports communication between Java programs over a network using the `java.rmi` package.

RMI facilitates the creation of distributed, multi-tier applications in which the logic and data of Java programs can be distributed across multiple machines on a network. It allows Java developers to build systems where components communicate seamlessly across network boundaries while maintaining the illusion of local method invocation.

## RMI Architecture

The RMI architecture consists of several key components that work together to enable remote communication:

### Core Components

1. **Client**
   - The application that initiates requests for remote method execution
   - Communicates with remote objects through stub proxies
   - Contains the business logic that requires access to remote services
   - Handles the marshaling of method arguments before sending them over the network

2. **Server**
   - Hosts and registers remote objects
   - Processes method invocation requests from clients
   - Returns results back to clients through the skeleton mechanism
   - Manages the lifecycle of remote objects

3. **Stub**
   - A client-side proxy object that represents the remote object
   - Implements the same interfaces as the remote object
   - Marshals (serializes) method arguments
   - Forwards requests to the skeleton on the server side
   - Unmarshals (deserializes) return values from the server

4. **Skeleton**
   - A server-side proxy object that represents the remote object to the RMI runtime
   - Receives requests forwarded by the stub through the transport layer
   - Unmarshals method arguments
   - Invokes the actual method on the real remote object
   - Marshals return values and exceptions back to the client

5. **Remote Reference Layer (RRL)**
   - Manages virtual connections between client and server
   - Handles object serialization and deserialization
   - Maintains semantic information about remote object references
   - Provides failure detection and reconnection mechanisms
   - Bridges the gap between high-level remote invocation and low-level transport protocols

6. **Transport Layer**
   - Handles the actual network communication between client and server systems
   - Manages socket connections and protocol negotiation
   - Implements connection pooling and multiplexing
   - Ensures reliable delivery of messages over the network
   - Uses TCP/IP for communication by default

### Architecture Flow

The typical RMI communication flow:

1. Client invokes a method on the stub object
2. Stub marshals the method arguments and invokes the RRL
3. RRL forwards the request through the transport layer to the server
4. Transport layer delivers the request to the server's skeleton
5. Skeleton unmarshals the arguments and invokes the actual remote method
6. Remote object executes the method and returns results
7. Skeleton marshals the return value and sends it back through RRL
8. Transport layer delivers the response to the client
9. RRL delivers the unmarshaled return value to the stub
10. Stub returns the result to the client application

## Project Structure

This practical demonstrates distributed computing concepts through the development of separate RMI Client and Server projects.

### Project Components

```
RMIClient/
├── pom.xml                           # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/hnys/bcd/
│   │   │       ├── RMIClient.java    # Main client application
│   │   │       ├── client/
│   │   │       │   ├── Message.java  # Remote interface for message service
│   │   │       │   └── UserService.java # Remote interface for user operations
│   │   │       └── model/
│   │   │           ├── User.java     # User data model
│   │   │           └── Data.java     # Generic data model
│   │   └── resources/
│   └── test/
│       └── java/
└── target/                           # Build output
```

### Remote Interfaces

#### Message Interface
Defines basic remote messaging operations:
- `hello()` - Returns a greeting message
- `getData()` - Retrieves data object from the server
- `getResult(int num1, int num2)` - Performs remote calculation

#### UserService Interface
Defines CRUD operations for user management:
- `getUser(int id)` - Retrieves a specific user by ID
- `addUser(Integer id, User user)` - Creates a new user
- `updateUser(Integer id, User user)` - Updates an existing user
- `deleteUser(int id)` - Removes a user
- `getAllUsers()` - Retrieves all users from the system

### Model Classes

**User** - Serializable entity representing a user
- `id` (int) - Unique identifier
- `name` (String) - User's full name
- `address` (String) - User's address
- `email` (String) - User's email address

**Data** - Serializable entity for generic data transfer
- `id` (int) - Data identifier
- `name` (String) - Data name

## Client Implementation

The RMIClient class demonstrates how to connect to a remote RMI server and invoke remote methods. The client uses JNDI (Java Naming and Directory Interface) for locating and binding remote services.

### Connection Methods Demonstrated

1. **Direct Registry Lookup** (Commented in code)
```java
Registry registry = LocateRegistry.getRegistry("localhost", 1099);
Message message = (Message) registry.lookup("message_service");
```

2. **Naming Service Lookup** (Commented in code)
```java
UserService userService = (UserService) Naming.lookup("rmi://127.0.0.1:1099/user_service");
```

3. **JNDI Context Lookup** (Active implementation)
```java
Properties prop = new Properties();
prop.put(Context.PROVIDER_URL, "rmi://127.0.0.1:1099");
prop.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");

InitialContext initialContext = new InitialContext(prop);
UserService userService = (UserService) initialContext.lookup("user_service");
```

### Client Operations

The client demonstrates:
1. Adding users to the remote service
2. Retrieving a specific user by ID
3. Deleting a user
4. Retrieving all users from the system
5. Displaying user information in formatted output

## Technical Specifications

- **Language**: Java 17
- **Build Tool**: Maven 4.0.0
- **Encoding**: UTF-8
- **RMI Registry Port**: 1099 (default)
- **Server Address**: 127.0.0.1 (localhost)

## Key Concepts Demonstrated

### Remote Objects
- Objects that implement `Remote` interface
- Can be invoked from different JVMs across the network
- All methods throw `RemoteException` to handle network-related failures

### Serialization
- Model classes implement `Serializable` interface
- Enables objects to be transmitted over the network
- Critical for passing object parameters and return values

### Stub and Skeleton Mechanism
- Stubs are generated automatically by Java RMI
- Provide transparent remote communication to the client
- Handle marshaling/unmarshaling of parameters and return values

### JNDI Registry
- Provides a naming service for remote objects
- Allows clients to locate and bind to remote services
- Uses the RMI registry running on port 1099 by default

## Prerequisites for Running

1. **RMI Server** - A corresponding RMIServer application must be running
2. **RMI Registry** - The RMI registry service must be active on port 1099
3. **Network Access** - Client must have network access to the server host
4. **Java Runtime** - Java 17 or later installed on the client machine

## Compilation and Execution

### Build
```bash
mvn clean compile
```

### Package
```bash
mvn clean package
```

### Run Client
```bash
mvn exec:java -Dexec.mainClass="com.hnys.bcd.RMIClient"
```

Or directly:
```bash
java -cp target/classes com.hnys.bcd.RMIClient
```

## Distributed Computing Concepts

This practical illustrates fundamental distributed computing principles:

1. **Location Transparency** - Clients invoke remote methods as if they were local
2. **Protocol Abstraction** - Network communication details are hidden from the application
3. **Service Registry** - Central location for registering and discovering services
4. **Failure Handling** - `RemoteException` provides mechanism for handling network failures
5. **Object Serialization** - Enables complex data types to be transmitted across the network
6. **Multi-tier Architecture** - Separates client logic from server business logic

## Related Components

This RMIClient project is part of a complete RMI implementation that includes:
- **RMIServer** - The corresponding server component that hosts remote objects
- **Shared Interfaces** - Common definitions for remote services
- **Model Classes** - Serializable objects for data transfer

## Summary

This practical demonstrates how Java RMI enables seamless distributed application development by allowing Java objects in different JVMs to communicate transparently. Through the implementation of remote service interfaces and the use of the RMI registry, developers can build scalable, distributed systems while maintaining the familiar Java object-oriented programming model.

The client-server architecture shown here is fundamental to understanding more complex distributed systems and enterprise application development patterns built on similar principles.
