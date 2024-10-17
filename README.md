# **Gaffer**

Welcome to Gaffer! This guide will help you set up the project locally on your machine.

---

## **Local Setup**

### **Windows**

1. **Download Git**
   - [Download Git](https://git-scm.com/download/win) and follow the instructions to install.

2. **Download Java 21**
   - [Download Java 21](https://www.oracle.com/ie/java/technologies/downloads/#jdk21-windows) and select the x64 installer.

3. **Install Maven**
   - [Download Maven 3.9.9](https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.zip).
   - Move the zip file to a desired location (e.g., Documents).
   - Extract the contents.
   - Navigate to the `bin` directory.
   - Copy the path to the `bin` folder from the Windows Explorer address bar.
   - Press the Windows key, search for "Environment Variables", and select the `Path` option under System or User variables.
   - Add a new entry and paste the path to the `bin` folder.
   - Open the terminal and type `mvn -v` to verify the installation. The output should look something like this:

     ```
     Maven home: /opt/homebrew/Cellar/maven/3.9.9/libexec
     Java version: 22.0.2, vendor: Homebrew, runtime: /opt/homebrew/Cellar/openjdk/22.0.2/libexec/openjdk.jdk/Contents/Home
     Default locale: en_IE, platform encoding: UTF-8
     OS name: "mac os x", version: "14.4.1", arch: "aarch64", family: "mac"
     ```
   - As long as you have a Java version above 21, you're good to go!

---

### **Mac**

1. **Install Homebrew**
   - Open the terminal and run the following command:
     ```bash
     /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
     ```

2. **Install Git**
   - Run the command:
     ```bash
     brew install git
     ```

3. **Install Java 21**
   - Run the command:
     ```bash
     brew install openjdk@21
     ```

4. **Install Maven**
   - Run the command:
     ```bash
     brew install maven
     ```

---

### **Running the App**

1. **Clone the Repository**
   - You may need to create an API key since the repo is private. Follow the [steps here](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-fine-grained-personal-access-token). Use your GitHub username when authenticating, and use the API key as the password.
   - Navigate to your Documents folder:
     ```bash
     cd ~/Documents
     ```
   - Clone the repo:
     ```bash
     git clone https://github.com/2000rosser/Gaffer.git
     ```

2. **Navigate Inside the Project Directory**
   - Move into the project directory:
     ```bash
     cd ~/Documents/Gaffer
     ```

3. **Deploy the Project**
   - Run the project:
     ```bash
     mvn spring-boot:run
     ```

4. **Access the App**
   - Open your web browser and go to: `http://localhost:8080`

5. **Login**
   - Use the email `a@a.com` with the password `a` to log in, or create a new user.

6. **Email Verification**
   - Email verification is disabled by default. To test it, use `mailhog` and set `email.verification` to true in `application.properties`

7. **LocalStack (s3 bucket)**
   - https://docs.localstack.cloud/user-guide/aws/s3/
   - `podman pull localstack/localstack:s3-latest`
   - `podman run --rm -p 4566:4566 localstack/localstack:s3-latest`
   - `aws configure` use dummy for secrets
   - `aws --endpoint-url=http://localhost:4566 s3 mb s3://my-local-bucket`
   

---
