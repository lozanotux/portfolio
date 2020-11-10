from django.shortcuts import render
from django.http import HttpResponse

# PDF Imports
from io import BytesIO
from reportlab.pdfgen import canvas
from reportlab.lib.pagesizes import A4
from reportlab.lib.units import cm
from reportlab.lib.styles import getSampleStyleSheet
from reportlab.platypus import Paragraph, Table, TableStyle, Image
from reportlab.lib.enums import TA_CENTER
from reportlab.lib import colors


def index(request):
    return render(request, template_name='polls_index.html')


def report(request):
    # Create the HttpResponse headers with PDF
    response = HttpResponse(content_type='application/pdf')
    response['Content-Disposition'] = 'attachment; filename=Platzi-student-report.pdf'

    # Create the PDF Object using the BytesIO object as its "file".
    buffer = BytesIO()
    c = canvas.Canvas(buffer, pagesize=A4)

    # Header
    c.setLineWidth(.3)
    c.setFont('Helvetica', 22)
    c.drawString(30, 750, 'Platzi')
    c.setFont('Helvetica', 12)
    c.drawString(30, 735, 'Report')

    c.setFont('Helvetica-Bold', 12)
    c.drawString(480, 750, "01/07/2020")
    # Start [X, Height], End [Y, Height]
    c.line(460, 747, 560, 747)

    # Students data (Normally, this data is obtained from a database)
    students = [
        {'#': '1', 'name': 'Miguel Nieva', 'b1': '3.4', 'b2': '2.2', 'b3': '4.5', 'total': '3.36'},
        {'#': '2', 'name': 'Sacha Lifszyc', 'b1': '4.3', 'b2': '2.6', 'b3': '4.6', 'total': '3.83'},
        {'#': '3', 'name': 'Carlos Jiménez', 'b1': '2.1', 'b2': '4.3', 'b3': '4.9', 'total': '3.76'},
        {'#': '4', 'name': 'Raquel Hernandez', 'b1': '5.0', 'b2': '4.7', 'b3': '4.4', 'total': '4.7'},
        {'#': '5', 'name': 'Elizabeth Rangel', 'b1': '3.3', 'b2': '4.9', 'b3': '4.9', 'total': '4.36'},
    ]

    #Table Header
    styles = getSampleStyleSheet()
    styleBH = styles["Normal"]
    styleBH.alignment = TA_CENTER
    styleBH.fontSize = 10

    numero = Paragraph('''#''', styleBH)
    alumno = Paragraph('''Alumno''', styleBH)
    b1 = Paragraph('''BIM1''', styleBH)
    b2 = Paragraph('''BIM2''', styleBH)
    b3 = Paragraph('''BIM3''', styleBH)
    total = Paragraph('''Promedio''', styleBH)

    data = []
    data.append([numero, alumno, b1, b2, b3, total])

    # Table Body
    styles = getSampleStyleSheet()
    styleN = styles["BodyText"]
    styleN.alignment = TA_CENTER
    styleN.fontSize = 7

    width, height = A4
    high = 650
    """ The size of table is relative to the amount of elements (rows), and
        its necessary subtract certain pixels for each row that we have.
    """
    for student in students:
        this_student = [student['#'], student['name'], student['b1'], student['b2'], student['b3'], student['total']]
        data.append(this_student)
        high = high - 18

    # Table Size
    width, height = A4
    table = Table(data, colWidths=[1.9 * cm, 9.5 * cm, 1.9 * cm, 1.9 * cm, 1.9 * cm, 2.1 * cm])
    table.setStyle(TableStyle(
        [
            # Estilos de la tabla
            ('INNERGRID', (0, 0), (-1, -1), 0.25, colors.black),
            ('BOX', (0, 0), (-1, -1), 0.25, colors.black),
        ]
    ))

    # PDF Size
    table.wrapOn(c, width, height)
    table.drawOn(c, 30, high)
    c.showPage() # Save Page

    # Save PDF
    c.save()
    
    # Get the value of BytesIO buffer and write response
    pdf = buffer.getvalue()
    buffer.close()
    response.write(pdf)
    return response
