# Intro

## Table of Contents

## Vocabulary

1. safety: Prevent failure or damage to the system affecting the user or the environment no matter how it is caused.

2. functional safety: Ensures that the system operates correctly in response to its inputs, including safe management of likely operator errors, hardware failures, and environmental changes. The absence of unreasonable risk due to hazards caused by malfunctioning behavior of E/E systems.

3. active safety: Some features like Adaptive Cruise Control (ACC) and Lane Keeping Assist (LKA) that help the driver to avoid accidents helps to prevent or mitigate accidents.

4. security: Prevent unauthorized access to the system.

## Electrical / Electronic Systems(EE Systems)

ABS (Anti-lock Braking System) is an example of an EE system. It is a safety-critical system that prevents the wheels from locking up during braking, thereby maintaining tractive contact with the road surface.

airbag systems are another example of EE systems that are designed to protect the driver and passengers in the event of a collision.

it must be insured that the system is safe and secure to prevent accidents and unauthorized access or any failure that may affect the user or the environment caused by Hardware failures, software bugs, or environmental changes.

## ARIANE 5

Ariane 5 was a European Space Agency (ESA) rocket that exploded 40 seconds after launch on its maiden flight in 1996. The cause of the explosion was a software bug in the inertial reference system (IRS) software. The software was designed for the Ariane 4 rocket, which had a different flight profile than the Ariane 5. The bug caused the IRS to output a value that was too large(over flow) for the flight control system to handle, resulting in the rocket's destruction.

no impact analysis was done on the software when it was reused in the Ariane 5, and the software was not designed to handle the Ariane 5's flight profile.

when a system is developed for systems that are safety-critical, Impact analysis must be done to ensure that the system is safe and secure if it is reused in a different system.

## ISO 26262

ISO 26262 is an international standard for functional safety in the automotive industry. It provides guidelines for the development of safety-critical systems in road vehicles, including cars, trucks, and buses.

The standard defines safety goals, safety requirements, and safety mechanisms to ensure that the system operates correctly in response to its inputs, including safe management of likely operator errors, hardware failures, and environmental changes.

The standard also provides guidelines for the development of safety-critical software, including requirements for software architecture, software design, and software testing.

### ISO parts

- Part 1: Vocabulary
- Part 2: Management of functional safety
- Part 3: Concept phase
- Part 4: Product development at the software level
- Part 5: Product development at the hardware level

#### PART 1: Vocabulary

1. Item:

    - is a system or array of systems to implement a function in a vehicle level such as a brake system or an airbag system but rare view camera is not an item but system as the camera itself doesn't implement a function or feature alone on the vehicle level.
    - wash and wiper system is an item and system as it implements a function on the vehicle level and it is a system itself.

2. System:

    - set of elements that interact to achieve a common goal.
    - it consists of sensor, controller, and actuator.
    - sensor could be an input signal and actuator could be an output signal.
    - not each system is an item but each item is a system or an array of systems.
    - gear box is a system but not an item.

3. Element: sensor, controller, actuator, or hardware component.

item -> system -> element

#### PART 3: Concept phase

- Concept phase is the first phase of the development process where the system requirements and goals are defined and the system architecture is designed.

##### 3.1. item definition

- HARA (Hazard and Risk Analysis) is a process that identifies the hazards that could cause harm to the user or the environment and assesses the risk associated with those hazards to determine the safety requirements for the item.

1. function definition: what the item should do.
2. scope and boundary: what the item should not do.
3. operation modes: how the item should operate in different conditions(sleep mode, normal mode, emergency mode).

- **Merrorless camera system**

1. function definition: to provide a rear and side view of the vehicle to the driver. perform manual and automatic camera defrosting. auto brightness and contrast adjustment. blind spot detection.
2. scope and boundary:
    boundary:camera and display system only. no audio system.
    scope: momken led to display warning messages.
3. operation modes:
    manual - auto

##### 3.2. ASIL determination

- ASIL (Automotive Safety Integrity Level) is a classification system for safety-critical systems in road vehicles. It provides guidelines for the development of safety-critical systems in road vehicles, including cars, trucks, and buses.
- ASIL A: minor injury -> rare light
- ASIL B: moderate injury -> rare camera
- ASIL C: severe injury ->
- ASIL D: fatal injury -> Anti-lock Braking System (ABS) and airbag systems

not only the need of system to operate on correct inputs but also not to operate on incorrect inputs.

ASIL D is the highest level of safety integrity and requires the most stringent safety requirements.

##### 3.3. Hazard analysis

System failure + Driver situation + controlability = Hazard

##### 3.4. Risk assessment

1. probability of the hazard occurring and it's determined in ISO(E1, E2, E3, E4). E4 is the highest level of probability.
2. controllability of the hazard by the driver and it's determined in ISO(C1, C2, C3, C4).
3. severity of the hazard and it's determined in ISO(S1, S2, S3, S4). S4 is the highest level of severity.

if in QM (Quality Management) the risk is acceptable then the system is safe.

Test case senarios are created to test the system in different conditions.

#### HARA

1. function: rear and side view of the vehicle to the driver
2. malfunction types: freezed view
3. effect at vehicle level: collision
4. driving situation: highway
5. possible control action: maneuvering
6. severity: high S3
7. exposure: high E4
8. controllability: medium C2

#### HARA

1. function: blind spot detection
2. malfunction types: NO detection
3. effect at vehicle level: collision
4. driving situation: highway
5. possible control action: maneuvering
6. severity: high weak S2
7. exposure: low E4
8. controllability: AWARENESS C1

#### HARA

1. function: rear and side view of the vehicle to the driver
2. malfunction types: black screen
3. effect at vehicle level: collision
4. driving situation: highway
5. possible control action: maneuvering
6. severity:S3
7. exposure: E4
8. controllability: c1 as i have no view

for each malfunction HARA is done to determine the severity, exposure, and controllability of the hazard.

##### 3.5. Safety goals

1. define the safety goals and requirements for error detection and correction, fault tolerance, and fail-safe operation.
2. define safety mechanisms to ensure that the system operates correctly in response to its inputs, including safe management of likely operator errors, hardware failures, and environmental changes.

## Concept phase output

1. item definition
2. HARA
3. ASIL determination
4. Goal definition

## System development

