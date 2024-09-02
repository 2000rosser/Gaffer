# Gaffer

## Local setup

### Windows

1. Download git
> [Git](https://git-scm.com/download/win) Follow instructions here

1. Download Java 21
> [Java 21](https://www.oracle.com/ie/java/technologies/downloads/#jdk21-windows) Select the x64 installer

1. Install maven
> [Maven 3.9.9](https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.zip) Move the zip file out of downloads (Documents or something)
> Extract it
> Navigate inside the `bin` directory
> Click the url at the top of windows explorer to get the full path (it should end in /bin)
> Press the windows key and search for environment variables.
> Select the path option for either System or User variables.
> Add a new option and paste the url to the /bin folder
> Open terminal and type mvn -v
Output should be like:
```
Maven home: /opt/homebrew/Cellar/maven/3.9.9/libexec
Java version: 22.0.2, vendor: Homebrew, runtime: /opt/homebrew/Cellar/openjdk/22.0.2/libexec/openjdk.jdk/Contents/Home
Default locale: en_IE, platform encoding: UTF-8
OS name: "mac os x", version: "14.4.1", arch: "aarch64", family: "mac"
```
As long as you have a Java version above 21 your good to go

### Mac
1. Install homebrew - Open terminal and paste the command below in then press enter\
`/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"`
1. Install Git - to do this paste the command below then press enter\
`brew install git`
1. Install Java 21 - to do this paste the command below then press enter\
`brew install openjdk@21`
1. Install maven - to do this paste the command below then press enter\
`brew install maven`

### Running the App
1. Clone the repo into your documents folder. You will probably need to create an API key since repo is private [Steps here](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-fine-grained-personal-access-token). Use your normal username when authenticating and then use the API key when prompted for password.\
`cd ~/Documents` then\
`git clone https://github.com/2000rosser/Gaffer.git`
1. Navigate inside project directory\
`cd ~/Documents/Gaffer`
1. Deploy the project\
`mvn spring-boot:run`
1. Navigate to `http://localhost:8080` in your web browser
1. You can use email address `a@a.com` with password `a` to login.
1. Email verification wont work unless you have `mailhog` setup