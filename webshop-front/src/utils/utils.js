import dayjs from "dayjs";

export const currencyFormatter = new Intl.NumberFormat("de-DE", {
  currency: "EUR",
  style: "currency",
  minimumFractionDigits: 0,
});

export const dateFormatter = (date) => {
  return dayjs(date).format("DD/MM/YYYY");
};

export const listFormatter = (list) => {
  let newList = [];
  let firstItem = true;

  list.forEach((element) => {
    let newElement;

    if (firstItem) {
      newElement = element;
      firstItem = false;
    } else {
      newElement = `, ${element}`;
    }

    newList.push(newElement);
  });

  return newList;
};

export const productTypeFormatter = (productType) => {
  switch (productType) {
    case "books":
      return "Books";
    case "footwear":
      return "Footwear";
    case "clothes":
      return "Clothes";
    case "technology":
      return "Technology";
    case "pets":
      return "Pets";
    case "vehicles":
      return "Vehicles";
    case "audio":
      return "Audio";
    case "sport":
      return "Sport";
    case "furniture":
      return "Furniture";
    case "mobilePhones":
      return "Mobile Phones";
    case "realEstate":
      return "Real Estate";
    default:
      return "Other";
  }
};
