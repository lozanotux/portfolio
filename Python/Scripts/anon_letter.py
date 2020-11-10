import string

def anon_letter(magazine, note):
    catalog = dict()

    for character in magazine:
        if not character in catalog.keys():
            catalog[character] = 1
        else:
            catalog[character] = catalog[character] + 1
    
    for character in note:
        if character in catalog.keys():
            if catalog[character] >= 1:
                catalog[character] = catalog[character] - 1
            else:
                return 'No {} available'.format(character)
        else:
            return 'There is no character {} in the magazine'.format(character)
    
    return 'Text Complete!'


if __name__ == '__main__':
    magazine = """
       SOME_LARGE_TEXT_FROM_A_MAGAZINE
    """

    magazine_cleaned = magazine.replace(string.whitespace, '')
    letter = 'Some phrase that I want to form with the characters of the magazine'
    letter_cleaned = letter.replace(string.whitespace, '')

    print(anon_letter(magazine_cleaned, letter_cleaned))
