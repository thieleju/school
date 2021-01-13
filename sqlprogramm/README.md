## Hinweise

Die jar "mysql-connector-java-8.0.22.jar" muss in der IDE eingebunden werden
(IDE muss wissen, dass es sich um ein Java projekt handelt und dass diese jar vom Projekt benutzt wird)

Wenn VSCode verwendet wird, kann die jar Datei mit diesem ".vscode/settings.json" Eintrag eingebunden werden.

```json
{
  "java.project.referencedLibraries": [
    "lib/**/*.jar",
    "sqlprogramm/lib/mysql-connector-java-8.0.22.jar"
  ]
}
```

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

## Dependency Management

The `JAVA DEPENDENCIES` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-pack/blob/master/release-notes/v0.9.0.md#work-with-jar-files-directly).
