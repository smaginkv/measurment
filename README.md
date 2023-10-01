// Уберём проблемы с (де)сериализацией. bff. Че за проблемы с сериализацией?
Вероятно речь идет про килосантиметры, которые как бы метры, но пользователь не поймет)

Преимущества кастомного десериализатора bff
MeasurementParseException - такого нет
SimpleQuantityFormat.getInstance() - сокращенный режим записи
Scale - сокращенная запись без scale

//пошто?
SimpleUnitFormat.getInstance().label(CLDR.FOOT, "ft");
SimpleUnitFormat.getInstance().label(CLDR.YARD, "yd");
SimpleUnitFormat.getInstance().label(CLDR.INCH, "in");
SimpleUnitFormat.getInstance().label(CLDR.MILE, "mi");

//Вот это прикольно, 1 километр, 25 метров
MixedQuantity

//Это своя единица измерения, объемный вес?
AnnotatedUnit
new AlternateUnit<Bmi>(Units.KILOGRAM.divide(Units.METRE.pow(2)), "В")))
Scale.RELATIVE