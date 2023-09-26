// Уберём проблемы с (де)сериализацией. bff. Че за проблемы с сериализацией?

JacksonConfiguration bff. О там еще и тесты есть для сериализатора. JacksonConfiguration в неге есть
MeasurementParseException - такого нет
SimpleQuantityFormat.getInstance() - сокращенный режим записи
Scale - сокращенная запись без scale


//пошто?
SimpleUnitFormat.getInstance().label(CLDR.FOOT, "ft");
SimpleUnitFormat.getInstance().label(CLDR.YARD, "yd");
SimpleUnitFormat.getInstance().label(CLDR.INCH, "in");
SimpleUnitFormat.getInstance().label(CLDR.MILE, "mi");


packageDto.setWeight(weight.to(Units.KILOGRAM).getValue().doubleValue()); - приведение к примитиву

Quantities.getQuantity(   new BigDecimal(totalGrossWeight.getAmount()) или Quantities.getQuantity(   totalGrossWeight.getAmount()

Quantities.getQuantity(
                        expectedUnitNetWeight.getValue(),
                        expectedUnitNetWeight.getUnit())

//расчитаем объем
lengthInMetres.multiply(widthInMetres, Area.class).multiply(heightInMetres, Volume.class);

//Вот это прикольно, 1 километр, 25 метров
MixedQuantity

//Это своя единица измерения, объемный вес?
AnnotatedUnit

//обсудить
Dimension, Unit, Quantity