# Module Layers

Module layers is a sbt plugin, which can check dependencies between modules against a defined architecture style.

Define a layer like api or domain for every module and define an architecture style. An architecture style consists of
permits, which defines allowed dependencies between different layers.