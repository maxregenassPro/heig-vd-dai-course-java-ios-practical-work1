package ch.heigvd.commands;

import picocli.CommandLine;

@CommandLine.Command(
	description = "A small TODO List CLI to experiment with Java IOs.",
	version = "1.0.0",
	scope = CommandLine.ScopeType.INHERIT,
	mixinStandardHelpOptions = true)
public class Root {
}
