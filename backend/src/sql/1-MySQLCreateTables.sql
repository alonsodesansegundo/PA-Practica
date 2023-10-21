ALTER TABLE Product DROP FOREIGN KEY ProductBidIdFK;

DROP TABLE Bid;
DROP TABLE Product;
DROP TABLE Category;
DROP TABLE User;

CREATE TABLE User (
    id BIGINT NOT NULL AUTO_INCREMENT,
    userName VARCHAR(60) COLLATE latin1_bin NOT NULL,
    password VARCHAR(60) NOT NULL,
    firstName VARCHAR(60) NOT NULL,
    lastName VARCHAR(60) NOT NULL,
    email VARCHAR(60) NOT NULL,
    role TINYINT NOT NULL,
    CONSTRAINT UserPK PRIMARY KEY (id),
    CONSTRAINT UserNameUniqueKey UNIQUE (userName)
) ENGINE = InnoDB;

CREATE INDEX UserIndexByUserName ON User (userName);

CREATE TABLE Category (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL,
    CONSTRAINT CategoryPK PRIMARY KEY (id),
    CONSTRAINT CategoryNameUniqueKey UNIQUE (name)
) ENGINE = InnoDB;

CREATE INDEX CategoryIndexByName ON Category (name);

CREATE TABLE Product (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    startingPrice DECIMAL(11, 2) NOT NULL,
    actualPrice DECIMAL(11, 2) NOT NULL,
    finishingDate DATETIME NOT NULL,
    startingDate DATETIME NOT NULL,
    deliveryInformation VARCHAR(2000) NOT NULL,
    categoryId BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    bidWinnerId BIGINT,
    remainingMinutes BIGINT DEFAULT 0,
    version BIGINT DEFAULT 0,
    CONSTRAINT ProductPK PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE INDEX ProductIndexByName ON Product (name);

CREATE TABLE Bid (
    id BIGINT NOT NULL AUTO_INCREMENT,
    dateBid DATETIME NOT NULL,
    maxPrice DECIMAL(11,2) NOT NULL,
    productId BIGINT NOT NULL,
    userBidId BIGINT NOT NULL,
    CONSTRAINT BidPK PRIMARY KEY (id)
) ENGINE = InnoDB;

/*Claves foraneas Bid*/
ALTER TABLE Bid ADD CONSTRAINT BidUserIdFK FOREIGN KEY (userBidId) REFERENCES User(id);
ALTER TABLE Bid ADD CONSTRAINT BidProductIdFK FOREIGN KEY (productId) REFERENCES Product(id);

/*Claves foraneas Product*/
ALTER TABLE Product ADD CONSTRAINT ProductCategoryIdFK FOREIGN KEY(categoryId) REFERENCES Category (id);
ALTER TABLE Product ADD CONSTRAINt ProductUserIdFK FOREIGN KEY(userId) REFERENCES User(id);
ALTER TABLE Product ADD CONSTRAINT ProductBidIdFK FOREIGN KEY (bidWinnerId) REFERENCES Bid(id);