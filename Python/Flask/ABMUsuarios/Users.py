#!/usr/bin/python3
# coding=utf-8

from sqlalchemy import Column, String, Integer, Date

from database import Base


class User(Base):
    __tablename__ = 'users'

    id = Column(Integer, primary_key=True)
    user_name = Column(String(32))
    user_age = Column(Integer)
    user_dni = Column(Integer)
    country = Column(String(16))

    def __init__(self, name, age, dni, country):
        self.user_name = name
        self.user_age = age
        self.user_dni = dni
        self.country = country
